package com.dragontrain.md.common.config.constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PathValidator.class)
public @interface Path {

	String message() default "올바르지 못한 PathVariable을 입력했습니다.";

	Class[] groups() default {};

	Class[] payload() default {};

	String[] candidates();
}
