package com.zf.controller.mock;

import com.zf.mock.IRequestService;
import com.zf.mock.RequestServiceFactory;
import com.zf.mock.enums.RequestType;
import com.zf.utils.GlobalConstants;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class MockController {

    @RequestMapping(value = "/mock")
    public String mock(HttpServletRequest request) throws Exception {
        String path = (String) request.getAttribute(GlobalConstants.REQUEST_PATH_KEY);
        String method = request.getMethod();
        String contentType = request.getContentType();
        if (contentType == null) {
            contentType = RequestType.CONTENT_TYPE_DETAULT;
        }
        RequestType requestType = RequestType.getRequestType(method, contentType);
        IRequestService requestService = RequestServiceFactory.getRequestServiceByType(requestType);
        String response = requestService.process(path, request);
        return response;
    }

}
