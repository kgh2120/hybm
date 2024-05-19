package com.dragontrain.md.common.config.constraint;

import java.lang.reflect.Field;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnumTypeValidator implements ConstraintValidator<EnumType, String> {

	private String[] names;

	@Override
	public void initialize(EnumType constraintAnnotation) {
		Field[] fields = constraintAnnotation.targetEnum().getDeclaredFields();
		names = new String[fields.length - 1];
		int index = 0;
		for (Field field : fields) {
			if (field.getName().equals("$VALUES"))
				continue;
			names[index++] = field.getName();
		}
	}

	@Override
	public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
		for (String name : names) {
			if (name.equals(s.toUpperCase()))
				return true;
		}
		return false;
	}
}
