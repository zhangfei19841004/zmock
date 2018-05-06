package com.zf.mock.impl;

import com.zf.mock.AbstractRequestService;
import com.zf.mock.enums.RequestType;
import com.zf.utils.CommonUtils;
import com.zf.zson.ZSON;
import com.zf.zson.result.ZsonResult;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class PostBodyRequestService extends AbstractRequestService {

    @Override
    public RequestType getRequestType() {
        return RequestType.POSTBODY;
    }

    private ZsonResult zsonResult;

    @Override
    public <T> boolean checkRequest(T requestInfo, String requestParamTemplate) {
        if (".*".equals(requestParamTemplate)) {
            return true;
        }
        ZsonResult requestInfoResult = ZSON.parseJson(requestInfo.toString());
        ZsonResult requestParamTemplateResult = ZSON.parseJson(requestParamTemplate);
        List<String> reqPaths = requestInfoResult.getPaths();
        Map<String, Class<?>> reqClassTypes = requestInfoResult.getClassTypes();
        List<String> tempPaths = requestParamTemplateResult.getPaths();
        Map<String, Class<?>> tempClassTypes = requestParamTemplateResult.getClassTypes();
        for (String tempPath : tempPaths) {
            if (!reqPaths.contains(tempPath) || !tempClassTypes.get(tempPath).equals(reqClassTypes.get(tempPath))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public <T> String getParamValueByKey(T requestInfo, String key) {
        if (".*".equals(key)) {
            return requestInfo.toString();
        }
        ZsonResult reqResult = ZSON.parseJson(requestInfo.toString());
        if (reqResult.getPaths().contains(key)) {
            return reqResult.getValue(key).toString();
        }
        return key;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getRequestInfo(HttpServletRequest request) throws Exception {
        return (T) CommonUtils.inputStreamToString(request.getInputStream());
    }

}
