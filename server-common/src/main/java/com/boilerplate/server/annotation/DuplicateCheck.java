package com.boilerplate.server.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DuplicateCheck {
    /**
     * 参数名称，作为校验key
     */
    String key();

    /**
     * 过期时间，单位秒，默认60秒
     */
    long expireTime() default 60;
}
