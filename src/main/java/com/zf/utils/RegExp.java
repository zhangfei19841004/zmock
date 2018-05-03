package com.zf.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExp {

    public static boolean match(String reg, String str) {
        return Pattern.matches(reg, str);
    }

    public static List<String> find(String reg, String str) {
        Matcher matcher = Pattern.compile(reg).matcher(str);
        List<String> list = new ArrayList<String>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }

    public static String findFirst(String reg, String str) {
        Matcher matcher = Pattern.compile(reg).matcher(str);
        while (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

}