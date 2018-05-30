package com.zf.mock;

import com.alibaba.fastjson.JSON;
import com.zf.dao.domain.MockInfoDao;
import com.zf.dao.domain.MockInfoDao.MockConditionInfo;
import com.zf.mock.enums.RequestType;
import com.zf.service.ConvertExpressionService;
import com.zf.service.MockInfoService;
import com.zf.service.ScriptExpressions;
import com.zf.utils.RegExp;
import com.zf.utils.ResponseUtil;
import com.zf.zson.ZSON;
import com.zf.zson.result.ZsonResult;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractRequestService implements IRequestService {

    @Autowired
    private MockInfoService mockInfoService;

    public abstract <T> boolean checkRequest(T requestInfo, String requestParamTemplate);

    public abstract <T> String getParamValueByKey(T requestInfo, String key);

    public abstract <T> T getRequestInfo(HttpServletRequest request) throws Exception;

    public <T> String process(String path, HttpServletRequest request) throws Exception {
        T requestInfo = this.getRequestInfo(request);
        ScriptExpressions scriptExpressions = new ScriptExpressions();
        scriptExpressions.getCtxt().set("params", requestInfo);
        scriptExpressions.getCtxt().set("headers", this.getHeadersInfo(request));
        MockInfoDao mockInfo = mockInfoService.getMockInfoDao(path, request.getMethod());
        if (mockInfo == null) {
            return JSON.toJSONString(ResponseUtil.getFailedResponse(ResponseUtil.ResponseConstants.NOMOCKINFO.getRetCode(), ResponseUtil.ResponseConstants.NOMOCKINFO.getRetMsg()));
        }
        if (!this.getRequestType().equals(RequestType.POSTBODY)) {
            if (!this.checkRequest(requestInfo, mockInfo.getRequestParamTemplate())) {
                return JSON.toJSONString(ResponseUtil.getFailedResponse("301", "请求参数不正确!"));
            }
        } else {
            try {
                ZsonResult zr = ZSON.parseJson(requestInfo.toString());
                scriptExpressions.getCtxt().set("params", zr);
                if (!this.checkRequest(requestInfo, mockInfo.getRequestParamTemplate())) {
                    return JSON.toJSONString(ResponseUtil.getFailedResponse("301", "请求参数不正确!"));
                }
            } catch (Exception e) {

            }
        }
        String response = null;
        if (StringUtils.isNotBlank(mockInfo.getRequestScript())) {
            String[] scripts = mockInfo.getRequestScript().split(";");
            for (String script : scripts) {
                try {
                    if(StringUtils.isBlank(script)){
                        continue;
                    }
                    scriptExpressions.execute(script.trim());
                    if (this.getRequestType().equals(RequestType.POSTBODY) && script.trim().matches("^params\\s*=.+")) {
                        requestInfo = (T) scriptExpressions.getCtxt().get("params");
                        if (!this.checkRequest(requestInfo, mockInfo.getRequestParamTemplate())) {
                            return JSON.toJSONString(ResponseUtil.getFailedResponse("301", "请求参数不正确!"));
                        }
                        if (!".*".equals(mockInfo.getRequestParamTemplate())) {
                            try {
                                ZsonResult zr = ZSON.parseJson(requestInfo.toString());
                                scriptExpressions.getCtxt().set("params", zr);
                            } catch (Exception e) {

                            }
                        }
                    }
                    if (scriptExpressions.getCtxt().get("response") != null) {
                        response = scriptExpressions.getCtxt().get("response").toString();
                        break;
                    }
                } catch (Exception e) {
                    return JSON.toJSONString(ResponseUtil.getFailedResponse("303", "请求脚本表达示不正确: " + script));
                }
            }
        }
        if(response==null){
            List<MockConditionInfo> conditions = mockInfo.getResponseCondition();
            if (conditions == null) {
                return JSON.toJSONString(ResponseUtil.getFailedResponse(ResponseUtil.ResponseConstants.NORESULT.getRetCode(), ResponseUtil.ResponseConstants.NORESULT.getRetMsg()));
            }
            String responseValue = null;
            for (MockConditionInfo mockConditionInfo : conditions) {
                String condition = mockConditionInfo.getResCondition();
                ConvertExpressionService<T> convert = new ConvertExpressionService<T>(requestInfo, this);
                String result = convert.convertExpression(condition);
                if (Boolean.valueOf(result)) {
                    responseValue = mockConditionInfo.getResValue();
                    break;
                }
            }
            if (responseValue == null) {
                return JSON.toJSONString(ResponseUtil.getFailedResponse(ResponseUtil.ResponseConstants.NORESULT.getRetCode(), ResponseUtil.ResponseConstants.NORESULT.getRetMsg()));
            }
            scriptExpressions.getCtxt().set("response", responseValue);
        }else{
            scriptExpressions.getCtxt().set("response", response);
        }
        if (StringUtils.isBlank(mockInfo.getResponseScript())) {
            return this.replaceResponse(scriptExpressions.getCtxt().get("response").toString(), scriptExpressions.getCtxt());
        }
        String[] scripts = mockInfo.getResponseScript().split(";");
        for (String script : scripts) {
            try {
                if (StringUtils.isNotBlank(script)) {
                    scriptExpressions.execute(script.trim());
                    scriptExpressions.getCtxt().set("response", this.replaceResponse(scriptExpressions.getCtxt().get("response").toString(), scriptExpressions.getCtxt()));
                }
            } catch (Exception e) {
                return JSON.toJSONString(ResponseUtil.getFailedResponse("304", "响应脚本表达示不正确: " + script));
            }
        }
        return scriptExpressions.getCtxt().get("response").toString();
    }

    private Map<String, String> getHeadersInfo(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    private String replaceResponse(String response, JexlContext ctxt) {
        List<String> replaces = RegExp.find("(?<=\"\\$\\{)[^\\}]+(?=\\}\")", response);
        for (String replace : replaces) {
            Object value = ctxt.get(replace);
            if (value == null) {
                Object params = ctxt.get("params");
                if (params instanceof Map) {
                    value = ((Map) params).get(replace);
                } else {
                    try{
                        value = ((ZsonResult) params).getValue(replace);
                    }catch (Exception e){

                    }
                }
            }
            if (value == null) {
                continue;
            }
            if (value instanceof String) {
                response = response.replace("\"${" + replace + "}\"", "\"" + value.toString() + "\"");
            } else {
                response = response.replace("\"${" + replace + "}\"", value.toString());
            }
        }
        replaces = RegExp.find("(?<=\\$\\{)[^\\}]+(?=\\})", response);
        for (String replace : replaces) {
            Object value = ctxt.get(replace);
            if (value == null) {
                Object params = ctxt.get("params");
                if (params instanceof Map) {
                    value = ((Map) params).get(replace);
                } else {
                    try{
                        value = ((ZsonResult) params).getValue(replace);
                    }catch (Exception e){

                    }
                }
            }
            if (value == null) {
                continue;
            }
            response = response.replace("${" + replace + "}", value.toString());
        }
        return response;
    }

}
