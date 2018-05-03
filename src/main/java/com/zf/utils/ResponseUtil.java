package com.zf.utils;

public class ResponseUtil {

    public static ResponseInfo getSuccessResponse() {
        return getResponseInfo(ResponseConstants.SUCCESS.getRetCode(), ResponseConstants.SUCCESS.getRetMsg(), null);
    }

    public static ResponseInfo getSuccessResponse(Object data) {
        return getResponseInfo(ResponseConstants.SUCCESS.getRetCode(), ResponseConstants.SUCCESS.getRetMsg(), data);
    }

    public static ResponseInfo getFailedResponse() {
        return getResponseInfo(ResponseConstants.FAILED.getRetCode(), ResponseConstants.FAILED.getRetMsg(), null);
    }

    public static ResponseInfo getFailedResponse(Object data) {
        return getResponseInfo(ResponseConstants.FAILED.getRetCode(), ResponseConstants.FAILED.getRetMsg(), data);
    }

    public static ResponseInfo getFailedResponse(String retCode, String retMsg) {
        return getResponseInfo(retCode, retMsg, null);
    }

    public static ResponseInfo getFailedResponse(String retCode, String retMsg, Object data) {
        return getResponseInfo(retCode, retMsg, data);
    }

    private static ResponseInfo getResponseInfo(String retCode, String retMsg, Object data) {
        return new ResponseInfo(retCode, retMsg, data);
    }

    public static class ResponseInfo {

        private String retCode;

        private String retMsg;

        private Object data;

        private ResponseInfo() {

        }

        private ResponseInfo(String retCode, String retMsg, Object data) {
            this.retCode = retCode;
            this.retMsg = retMsg;
            this.data = data;
        }

        public String getRetCode() {
            return retCode;
        }

        public void setRetCode(String retCode) {
            this.retCode = retCode;
        }

        public String getRetMsg() {
            return retMsg;
        }

        public void setRetMsg(String retMsg) {
            this.retMsg = retMsg;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

    }

    public static enum ResponseConstants {

        SUCCESS("200", "success."),

        FAILED("300", "failed."),

        NOMOCKINFO("301", "没有匹配的MOCK信息"),

        NORESULT("302", "没有匹配的响应值"),

        EXPERROR("303", "表达示不正确: "),

        EXCEPTION("400", "系统异常,请重试");

        private String retCode;

        private String retMsg;

        private ResponseConstants(String retCode, String retMsg) {
            this.retCode = retCode;
            this.retMsg = retMsg;
        }

        public String getRetCode() {
            return retCode;
        }

        public void setRetCode(String retCode) {
            this.retCode = retCode;
        }

        public String getRetMsg() {
            return retMsg;
        }

        public void setRetMsg(String retMsg) {
            this.retMsg = retMsg;
        }

    }

}
