package com.zf.mock;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.zf.dao.domain.MockInfoDao;
import com.zf.dao.domain.MockInfoDao.MockConditionInfo;
import com.zf.operators.OperatorHandlerFactory.UnequalHandler;
import com.zf.service.ConvertExpressionService;
import com.zf.service.MockInfoService;
import com.zf.utils.ResponseUtil;

public abstract class AbstractRequestService implements IRequestService{
	
	@Autowired
	private MockInfoService mockInfoService;
	
	public abstract <T> boolean  checkRequest(T requestInfo, String requestParamTemplate);
	
	public abstract <T> String getParamValueByKey(T requestInfo, String key);
	
	public abstract <T> T getRequestInfo(HttpServletRequest request) throws Exception;
	
	public <T> String process(String path, HttpServletRequest request) throws Exception {
		T requestInfo = this.getRequestInfo(request); 
		MockInfoDao mockInfo = mockInfoService.getMockInfoDao(path, request.getMethod());
		if(mockInfo==null){
			return JSON.toJSONString(ResponseUtil.getFailedResponse(ResponseUtil.ResponseConstants.NOMOCKINFO.getRetCode(), ResponseUtil.ResponseConstants.NOMOCKINFO.getRetMsg()));
		}
		if(!this.checkRequest(requestInfo, mockInfo.getRequestParamTemplate())){
			return JSON.toJSONString(ResponseUtil.getFailedResponse("301", "请求参数不正确!"));
		}
		List<MockConditionInfo> conditions = mockInfo.getResponseCondition();
		if(conditions==null){
			return JSON.toJSONString(ResponseUtil.getFailedResponse(ResponseUtil.ResponseConstants.NORESULT.getRetCode(), ResponseUtil.ResponseConstants.NORESULT.getRetMsg()));
		}
		for (MockConditionInfo mockConditionInfo : conditions) {
			String condition = mockConditionInfo.getResCondition();
			ConvertExpressionService<T> convert = new ConvertExpressionService<T>(requestInfo, this);
			String result = convert.convertExpression(condition);
			if(Boolean.valueOf(result)){
				return mockConditionInfo.getResValue();
			}
		}
		return JSON.toJSONString(ResponseUtil.getFailedResponse(ResponseUtil.ResponseConstants.NORESULT.getRetCode(), ResponseUtil.ResponseConstants.NORESULT.getRetMsg()));
	}
	
}
