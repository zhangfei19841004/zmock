package com.zf.operators;

import com.zf.exceptions.BusinessException;
import com.zf.operators.enums.OperatorsType;
import com.zf.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OperatorHandlerFactory {

    @Autowired
    private ApplicationContext applicationContext;

    private final static Map<OperatorsType, IOperatorHandler> OPERATOR_HANDLERS = new ConcurrentHashMap<OperatorsType, IOperatorHandler>();

    @PostConstruct
    public void init() {
        Map<String, IOperatorHandler> operatorServices = applicationContext.getBeansOfType(IOperatorHandler.class);
        for (Entry<String, IOperatorHandler> operatorService : operatorServices.entrySet()) {
            OPERATOR_HANDLERS.put(operatorService.getValue().getOperatorType(), operatorService.getValue());
        }
    }

    public static IOperatorHandler getRequestServiceByType(OperatorsType operatorsType) {
        return OPERATOR_HANDLERS.get(operatorsType);
    }

    @Service
    public static class UnequalHandler extends AbstractOperatorHandler {

        @Override
        public OperatorsType getOperatorType() {
            return OperatorsType.UNEQUAL;
        }

        @Override
        public boolean getCalculateResult(String[] exps) {
            return !exps[0].equals(exps[1]);
        }

    }

    @Service
    public static class EqualHandler extends AbstractOperatorHandler {

        @Override
        public OperatorsType getOperatorType() {
            return OperatorsType.EQUAL;
        }

        @Override
        public boolean getCalculateResult(String[] exps) {
            return exps[0].equals(exps[1]);
        }

    }

    @Service
    public static class GreaterHandler extends AbstractOperatorHandler {

        @Override
        public OperatorsType getOperatorType() {
            return OperatorsType.GREATER;
        }

        @Override
        public boolean getCalculateResult(String[] exps) {
            try {
                return Integer.parseInt(exps[0]) > Integer.parseInt(exps[1]);
            } catch (Exception e) {
                throw new BusinessException(ResponseUtil.ResponseConstants.EXPERROR.getRetCode(), ResponseUtil.ResponseConstants.EXPERROR.getRetMsg() + exps[0] + " " + this.getOperatorType().getOperator() + " " + exps[1]);
            }
        }

    }

    @Service
    public static class GreaterEqualHandler extends AbstractOperatorHandler {

        @Override
        public OperatorsType getOperatorType() {
            return OperatorsType.GREATER_EQUAL;
        }

        @Override
        public boolean getCalculateResult(String[] exps) {
            try {
                return Integer.parseInt(exps[0]) >= Integer.parseInt(exps[1]);
            } catch (Exception e) {
                throw new BusinessException(ResponseUtil.ResponseConstants.EXPERROR.getRetCode(), ResponseUtil.ResponseConstants.EXPERROR.getRetMsg() + exps[0] + " " + this.getOperatorType().getOperator() + " " + exps[1]);
            }
        }

    }

    @Service
    public static class LessHandler extends AbstractOperatorHandler {

        @Override
        public OperatorsType getOperatorType() {
            return OperatorsType.LESS;
        }

        @Override
        public boolean getCalculateResult(String[] exps) {
            try {
                return Integer.parseInt(exps[0]) < Integer.parseInt(exps[1]);
            } catch (Exception e) {
                throw new BusinessException(ResponseUtil.ResponseConstants.EXPERROR.getRetCode(), ResponseUtil.ResponseConstants.EXPERROR.getRetMsg() + exps[0] + " " + this.getOperatorType().getOperator() + " " + exps[1]);
            }
        }

    }

    @Service
    public static class LessEqualHandler extends AbstractOperatorHandler {

        @Override
        public OperatorsType getOperatorType() {
            return OperatorsType.LESS_EQUAL;
        }

        @Override
        public boolean getCalculateResult(String[] exps) {
            try {
                return Integer.parseInt(exps[0]) <= Integer.parseInt(exps[1]);
            } catch (Exception e) {
                throw new BusinessException(ResponseUtil.ResponseConstants.EXPERROR.getRetCode(), ResponseUtil.ResponseConstants.EXPERROR.getRetMsg() + exps[0] + " " + this.getOperatorType().getOperator() + " " + exps[1]);
            }
        }

    }

    @Service
    public static class InHandler extends AbstractOperatorHandler {

        @Override
        public OperatorsType getOperatorType() {
            return OperatorsType.IN;
        }

        @Override
        public boolean getCalculateResult(String[] exps) {
            try {
                List<String> list = new ArrayList<String>(Arrays.asList(exps[1].split("\\,")));
                return list.contains(exps[0]);
            } catch (Exception e) {
                throw new BusinessException(ResponseUtil.ResponseConstants.EXPERROR.getRetCode(), ResponseUtil.ResponseConstants.EXPERROR.getRetMsg() + exps[0] + " " + this.getOperatorType().getOperator() + " " + exps[1]);
            }
        }

    }

    @Service
    public static class ContainsHandler extends AbstractOperatorHandler {

        @Override
        public OperatorsType getOperatorType() {
            return OperatorsType.CONTAINS;
        }

        @Override
        public boolean getCalculateResult(String[] exps) {
            try {
                return exps[0].contains(exps[1]);
            } catch (Exception e) {
                throw new BusinessException(ResponseUtil.ResponseConstants.EXPERROR.getRetCode(), ResponseUtil.ResponseConstants.EXPERROR.getRetMsg() + exps[0] + " " + this.getOperatorType().getOperator() + " " + exps[1]);
            }
        }

    }

}
