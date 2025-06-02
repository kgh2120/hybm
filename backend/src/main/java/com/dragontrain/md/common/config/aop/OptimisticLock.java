package com.dragontrain.md.common.config.aop;

import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.Order;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OptimisticLock {
	@AliasFor("threadSleepTime")
	int value() default 50;
	@AliasFor("value")
	int threadSleepTime() default 50;

}
