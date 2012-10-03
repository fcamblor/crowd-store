package com.crowdstore.web.common.annotations;

import java.lang.annotation.*;

/**
 * @author fcamblor
 *         Annotation used on @Controller methods to describe urls accessible even if we are not authenticated
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PublicSpace {
}
