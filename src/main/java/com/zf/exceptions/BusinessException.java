package com.zf.exceptions;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String retCode;

    private String retMsg;

    public BusinessException(String retCode, String retMsg) {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public String getRetCode() {
        return retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

}

