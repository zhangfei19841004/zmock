package com.zf.dao.domain;

import java.util.List;

public class MockInfoDao {
	
	private Long id;
	
	private String requestUrl;
	
	private String requestMethod;
	
	private String requestParamTemplate;

	private String requestDecryptAndVerify;

	private String[] resCondition;

	private String[] resValue;

	private String responseEncrypt;

	private List<MockConditionInfo> responseCondition;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getRequestParamTemplate() {
		return requestParamTemplate;
	}

	public void setRequestParamTemplate(String requestParamTemplate) {
		this.requestParamTemplate = requestParamTemplate;
	}
	
	public List<MockConditionInfo> getResponseCondition() {
		return responseCondition;
	}

	public void setResponseCondition(List<MockConditionInfo> responseCondition) {
		this.responseCondition = responseCondition;
	}

	public String[] getResCondition() {
		return resCondition;
	}

	public void setResCondition(String[] resCondition) {
		this.resCondition = resCondition;
	}

	public String[] getResValue() {
		return resValue;
	}

	public void setResValue(String[] resValue) {
		this.resValue = resValue;
	}

	public String getRequestDecryptAndVerify() {
		return requestDecryptAndVerify;
	}

	public void setRequestDecryptAndVerify(String requestDecryptAndVerify) {
		this.requestDecryptAndVerify = requestDecryptAndVerify;
	}

	public String getResponseEncrypt() {
		return responseEncrypt;
	}

	public void setResponseEncrypt(String responseEncrypt) {
		this.responseEncrypt = responseEncrypt;
	}

	public static class MockConditionInfo {
		
		public String resCondition;
		
		public String resValue;

		public String getResCondition() {
			return resCondition;
		}

		public void setResCondition(String resCondition) {
			this.resCondition = resCondition;
		}

		public String getResValue() {
			return resValue;
		}

		public void setResValue(String resValue) {
			this.resValue = resValue;
		}
		
	}
	
}
