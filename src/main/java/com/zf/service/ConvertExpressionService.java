package com.zf.service;

import com.zf.exceptions.BusinessException;
import com.zf.mock.AbstractRequestService;
import com.zf.operators.IOperatorHandler;
import com.zf.operators.OperatorHandlerFactory;
import com.zf.operators.enums.OperatorsType;
import com.zf.utils.RegExp;
import com.zf.utils.ResponseUtil;

import java.util.List;
import java.util.regex.Pattern;

public class ConvertExpressionService<T> {

    private T requestInfo;

    private AbstractRequestService reqService;

    private final String trueFlag = "true";

    private final String falseFlag = "false";

    public ConvertExpressionService(T requestInfo, AbstractRequestService reqService) {
        super();
        this.requestInfo = requestInfo;
        this.reqService = reqService;
    }

    public String convertExpression(String expresses) {
        String express = null;
        while ((express = RegExp.findFirst("(?<=\\()[^\\(\\)]+(?=\\))", expresses)) != null) {
            String convert = this.handleExpression(express);
            expresses = expresses.replaceFirst("\\([^\\(\\)]+\\)", convert);
        }
        return this.handleExpression(expresses);
    }

    private String handleExpression(String express) {
        List<String> connector = RegExp.find("\\sand\\s|\\sor\\s", express);
        String[] exps = express.split("\\sand\\s|\\sor\\s");
        for (int i = 0; i < exps.length; i++) {
            exps[i] = this.calculateResult(exps[i].trim());
        }
        Boolean result = false;
        for (int i = 0; i < exps.length; i++) {
            if (i == 0) {
                result = Boolean.valueOf(exps[i]);
            } else {
                String conn = connector.get(i - 1);
                Boolean temp = Boolean.valueOf(exps[i]);
                if (conn.trim().equals("and")) {
                    result = result && temp;
                } else {
                    result = result || temp;
                }
            }
        }
        return result.toString();
    }

    private String calculateResult(String express) {
        if (trueFlag.equals(express)) {
            return trueFlag;
        } else if (falseFlag.equals(express)) {
            return falseFlag;
        }
        OperatorsType opType = OperatorsType.getOperatorType(express);
        if (opType == null) {
            throw new BusinessException(ResponseUtil.ResponseConstants.EXPERROR.getRetCode(), ResponseUtil.ResponseConstants.EXPERROR.getRetMsg() + express);
        }
        IOperatorHandler operatorHandler = OperatorHandlerFactory.getRequestServiceByType(opType);
        String[] exps = operatorHandler.getExpresses(express);
        if (exps.length != 2) {
            throw new BusinessException(ResponseUtil.ResponseConstants.EXPERROR.getRetCode(), ResponseUtil.ResponseConstants.EXPERROR.getRetMsg() + express);
        }
        exps[0] = exps[0].trim();
        exps[0] = reqService.getParamValueByKey(requestInfo, exps[0]);
        exps[1] = exps[1].trim();
        if (exps[1].matches("\".*\"") || exps[1].matches("\'.*\'")) {
            exps[1] = exps[1].substring(1, exps[1].length() - 1);
        }
        if (operatorHandler.getCalculateResult(exps)) {
            return trueFlag;
        } else {
            return falseFlag;
        }
    }

    public static void main(String[] args) {
        String s = ".";
        String e = "a !=\\. b !\\=c ";
        String[] es = e.split("(?<!\\\\)" + Pattern.quote(s));
        for (String string : es) {
            System.out.println(string);
        }
    }

}
