package com.zf.mock;

import com.zf.mock.enums.RequestType;

import javax.servlet.http.HttpServletRequest;

public interface IRequestService {

    RequestType getRequestType();

    <T> String process(String path, HttpServletRequest request) throws Exception;
}
