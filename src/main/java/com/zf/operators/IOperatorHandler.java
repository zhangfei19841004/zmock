package com.zf.operators;

import com.zf.operators.enums.OperatorsType;

public interface IOperatorHandler {

    OperatorsType getOperatorType();

    String[] getExpresses(String express);

    boolean getCalculateResult(String[] exps);
}
