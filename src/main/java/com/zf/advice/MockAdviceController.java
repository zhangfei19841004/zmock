package com.zf.advice;

import com.zf.exceptions.BusinessException;
import com.zf.utils.ResponseUtil;
import com.zf.utils.ResponseUtil.ResponseConstants;
import com.zf.utils.ResponseUtil.ResponseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;


@RestControllerAdvice(annotations = RestController.class)
public class MockAdviceController {

    private final static Logger logger = LoggerFactory.getLogger(MockAdviceController.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseInfo processException(NativeWebRequest request, Exception e) {
        if (e instanceof BusinessException) {
            logger.error(e.getMessage(), e);
            return ResponseUtil.getFailedResponse(((BusinessException) e).getRetCode(), ((BusinessException) e).getRetMsg());
        } else {
            logger.error(e.getMessage(), e);
            return ResponseUtil.getFailedResponse(ResponseConstants.EXCEPTION.getRetCode(), ResponseConstants.EXCEPTION.getRetMsg());
        }

    }

}
