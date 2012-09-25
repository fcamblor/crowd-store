package com.crowdstore.web.hello;

import javax.validation.constraints.NotNull;

/**
 * @author fcamblor
 */
public class Query {
    @NotNull
    Integer leftOperand;
    @NotNull
    Integer rightOperand;

    public Integer getLeftOperand() {
        return leftOperand;
    }

    public void setLeftOperand(Integer leftOperand) {
        this.leftOperand = leftOperand;
    }

    public Integer getRightOperand() {
        return rightOperand;
    }

    public void setRightOperand(Integer rightOperand) {
        this.rightOperand = rightOperand;
    }
}
