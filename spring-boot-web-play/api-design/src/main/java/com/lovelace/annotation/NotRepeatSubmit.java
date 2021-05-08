package com.lovelace.annotation;
/**
 * 禁止重复提交
 */
public @interface NotRepeatSubmit {
    /**
     * 过期时间，单位毫秒
     **/
    long value() default 5000;
}
