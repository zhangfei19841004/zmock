package com.zf.operators.enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum OperatorsType {

    UNEQUAL("!=", 0),
    EQUAL("==", 0),
    GREATER(">", 1),
    LESS("<", 1),
    GREATER_EQUAL(">=", 0),
    LESS_EQUAL("<=", 0),
    IN("in", 1),
    CONTAINS("contains", 0);

    private String operator;

    private int priority;

    public String getOperator() {
        return operator;
    }

    public int getPriority() {
        return priority;
    }

    OperatorsType(String operator, int priority) {
        this.operator = operator;
        this.priority = priority;
    }

    public static OperatorsType getOperatorType(String express) {
        List<OperatorsType> types = new ArrayList<OperatorsType>();
        for (OperatorsType operatorsType : OperatorsType.values()) {
            if (express.indexOf(operatorsType.getOperator()) != -1) {
                types.add(operatorsType);
            }
        }
        if (types.size() > 0) {
            Collections.sort(types, new Comparator<OperatorsType>() {
                @Override
                public int compare(OperatorsType o1, OperatorsType o2) {
                    return o1.getPriority() - o2.getPriority();
                }
            });
            return types.get(0);
        }
        return null;
    }

    public static void main(String[] args) {
        OperatorsType t = OperatorsType.getOperatorType("a!=b");
        System.out.println(t.getOperator());
    }

}
