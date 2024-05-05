package com.dragontrain.md.common.config.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PathValidator implements ConstraintValidator<Path, String> {

	private String[] candidates;

	@Override
	public void initialize(Path constraintAnnotation) {
		candidates = constraintAnnotation.candidates();
	}

	@Override
	public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
		for (String candidate : candidates) {
			if (candidate.equalsIgnoreCase(s)) {
				return true;
			}
		}
		return false;
	}
}
