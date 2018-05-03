package com.zf.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class CommonUtils {

    public static String inputStreamToString(InputStream in) throws IOException {
        byte[] buffer = new byte[1024];
        int read = 0;
        StringBuffer sb = new StringBuffer();
        while ((read = in.read(buffer)) != -1) {
            sb.append(new String(buffer, 0, read, "UTF-8"));
        }
        return sb.toString();
    }

    public static Map<String, String> getRequestParams(HttpServletRequest request) {
        Map<String, String> paramsMap = new HashMap<String, String>();
        Enumeration<String> paramsNames = request.getParameterNames();
        while (paramsNames.hasMoreElements()) {
            String key = paramsNames.nextElement();
            paramsMap.put(key, request.getParameter(key));
        }
        return paramsMap;
    }

}
