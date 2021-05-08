package org.jarvis.annotation;

import java.lang.annotation.*;

/**
 * 更改数据源注解,凡是被该注解标记的方法将会在该线程先设置数据源key,设置到{@code  ThreadLocal}
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ChangeDS {
    String value() default "default";
}
