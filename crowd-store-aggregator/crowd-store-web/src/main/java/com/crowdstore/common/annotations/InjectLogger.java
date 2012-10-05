package com.crowdstore.common.annotations;

import java.lang.annotation.*;

/**
 * @author fcamblor
 *         Annotation used to automatically inject a logger into the target field, allowing
 *         to use Loggers non statically, mainly to avoid drawbacks due to late initialization of the logger
 *         (frequent problem encountered when trying to use JUL which is part of the JDK and thus, cannot be
 *         overloaded by slf4j classpath trick)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface InjectLogger {
}
