package org.jarvis.sqltask.annotation;

import java.lang.annotation.*;

@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ChangeDS {
    String value() default "default";
}
