package com.zf.mock;

import javax.servlet.http.HttpServletRequest;

import com.zf.mock.enums.RequestType;

public interface IRequestService {
	
	RequestType getRequestType();

	<T> String process(String path, HttpServletRequest request) throws Exception;
}
