package com.zf.service;

import com.zf.utils.MD5Util;
import com.zf.utils.RegExp;
import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

import java.util.List;

/**
 * Created by zhangfei on 2018/4/18.
 */
public class Test {

    public static void main(String[] args) {
        JexlContext ctxt = new MapContext();
        ctxt.set("System",System.class);
        ctxt.set("MD5Util", MD5Util.class);
        ctxt.set("t", 2l);
        //ctxt.set("a", new String());
        new JexlEngine().createExpression("a = \"fff\";").evaluate(ctxt);
        new JexlEngine().createExpression("b=a+\"ggg\"").evaluate(ctxt);
        new JexlEngine().createExpression("c = 10").evaluate(ctxt);
        new JexlEngine().createExpression("d = c+20").evaluate(ctxt);
        new JexlEngine().createExpression("s = System.currentTimeMillis()").evaluate(ctxt);
        new JexlEngine().createExpression("e = b.substring(0,t)").evaluate(ctxt);
        new JexlEngine().createExpression("f = MD5Util.getStringMD5String(b)").evaluate(ctxt);
        Expression expression = new JexlEngine().createExpression("a = 10;");
        String s = expression.dump();
        Object ro = expression.evaluate(ctxt);

        System.out.println(ctxt.get("d"));
        System.out.println(ctxt.get("d").getClass());
        System.out.println(ctxt.get("s"));
        System.out.println(ctxt.get("e"));
        System.out.println(ctxt.get("f"));

        List<String> replaces = RegExp.find("(?<=\"\\$\\{)[^\\}]+(?=\\}\")", "\"${aa{a}\"${bbb}");
        System.out.println(replaces);
    }
}
