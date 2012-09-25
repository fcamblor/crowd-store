package com.crowdstore.web.hello;

/**
 * @author fcamblor
 */
public enum Operator {
    plus {
        @Override
        public Integer apply(Integer leftOperand, Integer rightOperand) {
            return leftOperand + rightOperand;
        }
    }, minus {
        @Override
        public Integer apply(Integer leftOperand, Integer rightOperand) {
            return leftOperand - rightOperand;
        }
    }, multiply {
        @Override
        public Integer apply(Integer leftOperand, Integer rightOperand) {
            return leftOperand * rightOperand;
        }
    }, divide {
        @Override
        public Integer apply(Integer leftOperand, Integer rightOperand) {
            return Math.round(leftOperand / rightOperand);
        }
    };

    public abstract Integer apply(Integer leftOperand, Integer rightOperand);
}
