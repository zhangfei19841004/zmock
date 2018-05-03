package com.zf.operators;

import java.util.regex.Pattern;

public abstract class AbstractOperatorHandler implements IOperatorHandler {

    @Override
    public String[] getExpresses(String express) {
        return express.split("(?<!\\\\)" + Pattern.quote(this.getOperatorType().getOperator()));
    }

}
