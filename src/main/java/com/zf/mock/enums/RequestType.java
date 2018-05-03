package com.zf.mock.enums;

public enum RequestType {

    GET("GET", RequestType.CONTENT_TYPE_DETAULT),
    POSTFORM("POST", RequestType.CONTENT_TYPE_FORM),
    POSTBODY("POST", RequestType.CONTENT_TYPE_DETAULT);

    private String method;

    private String contentType;

    public String getMethod() {
        return method;
    }

    public String getContentType() {
        return contentType;
    }

    private RequestType(String method, String contentType) {
        this.method = method;
        this.contentType = contentType;
    }

    final public static String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";

    final public static String CONTENT_TYPE_DETAULT = "";

    public static RequestType getRequestType(String method, String contentType) {
        for (RequestType requestType : RequestType.values()) {
            contentType = contentType.contains(CONTENT_TYPE_FORM) ? CONTENT_TYPE_FORM : CONTENT_TYPE_DETAULT;
            if (requestType.getMethod().equals(method.toUpperCase()) && requestType.getContentType().equals(contentType)) {
                return requestType;
            }
        }
        return null;
    }
}
