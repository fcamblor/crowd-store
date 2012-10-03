package com.crowdstore.common.security;

import com.crowdstore.models.security.ConditionnalOperator;
import com.crowdstore.models.security.GlobalAuthorization;

import java.util.Arrays;

/**
 * @author fcamblor
 */
public class RestrictedFeatureException extends RuntimeException {
    private GlobalAuthorization[] requiredAuthorizations;
    private ConditionnalOperator operator;

    public RestrictedFeatureException(GlobalAuthorization[] requiredAuthorizations, ConditionnalOperator operator) {
        super(buildMessage(requiredAuthorizations, operator));
        this.requiredAuthorizations = requiredAuthorizations;
        this.operator = operator;
    }

    private static String buildMessage(GlobalAuthorization[] requiredAuthorizations, ConditionnalOperator operator) {
        return String.format("Feature needs following authorizations which didn't match with current user authorizations : %s (operator : %s)",
                Arrays.toString(requiredAuthorizations), operator.toString());
    }
}
