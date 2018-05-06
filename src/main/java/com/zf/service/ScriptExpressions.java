package com.zf.service;

import com.zf.utils.MD5Util;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by zhangfei on 2018/4/8/008.
 */
public class ScriptExpressions {

    private JexlContext ctxt;

    public JexlContext getCtxt() {
        return ctxt;
    }

    public ScriptExpressions() {
        ctxt = new MapContext();
        ctxt.set("System", System.class);
        ctxt.set("MD5Util", MD5Util.class);
        ctxt.set("StringUtils", StringUtils.class);
    }

    public Object execute(String express) {
        return new JexlEngine().createExpression(express).evaluate(ctxt);
    }


}
