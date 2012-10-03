package com.crowdstore.web.common.annotations;

import com.crowdstore.models.security.ConditionnalOperator;
import com.crowdstore.models.security.GlobalAuthorization;

import java.lang.annotation.*;

/**
 * @author fcamblor
 *         Annotation used on @Controller methods to describe authorizations needed to access current method
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireGlobalAuthorizations {
    GlobalAuthorization[] value() default {};

    ConditionnalOperator operator() default ConditionnalOperator.OR;
}
