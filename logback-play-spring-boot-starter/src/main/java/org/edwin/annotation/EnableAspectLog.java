package org.edwin.annotation;

import org.edwin.selector.AspectSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(AspectSelector.class)
@Documented
public @interface EnableAspectLog {
    //传入包名
    String[] packages() default "";
}
