package com.zf.mock;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zf.decryptandverify.DecryptAndVerifyFactory;
import com.zf.decryptandverify.EncryptFactory;
import com.zf.decryptandverify.IDecryptAndVerify;
import com.zf.decryptandverify.IEncrypt;
import org.apache.commons.lang3.StringUtils;
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
		if(StringUtils.isNotBlank(mockInfo.getRequestDecryptAndVerify()) && DecryptAndVerifyFactory.getDecryptAndVerifySize()>0){
			IDecryptAndVerify decryptAndVerify = DecryptAndVerifyFactory.getDecryptAndVerify(mockInfo.getRequestDecryptAndVerify());
			if(decryptAndVerify==null){
				return JSON.toJSONString(ResponseUtil.getFailedResponse("302", "请求参数验证文件失效!"));
			}
			if(!decryptAndVerify.verify(requestInfo, mockInfo)){
				return JSON.toJSONString(ResponseUtil.getFailedResponse("302", "请求参数验证失败!"));
			}
			requestInfo = decryptAndVerify.decrypt(requestInfo, mockInfo);
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
				if(StringUtils.isNotBlank(mockInfo.getResponseEncrypt()) && EncryptFactory.getEncryptSize()>0){
					IEncrypt encrypt = EncryptFactory.getEncrypt(mockInfo.getResponseEncrypt());
					if(encrypt==null){
						return JSON.toJSONString(ResponseUtil.getFailedResponse("302", "响应参数加密文件失效!"));
					}
					encrypt.encrypt(mockConditionInfo.getResValue(), mockInfo);
				}
				return mockConditionInfo.getResValue();
			}
		}
		return JSON.toJSONString(ResponseUtil.getFailedResponse(ResponseUtil.ResponseConstants.NORESULT.getRetCode(), ResponseUtil.ResponseConstants.NORESULT.getRetMsg()));
	}
	
}
