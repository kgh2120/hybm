package com.dragontrain.md.domain.food.exception;

import com.dragontrain.md.common.config.exception.CustomException;
import com.dragontrain.md.common.config.exception.ErrorCode;

public class FoodException extends CustomException {
	public FoodException(ErrorCode errorCode) {
		super(errorCode);
	}

	public FoodException(ErrorCode errorCode, String loggingMessage) {
		super(errorCode, loggingMessage);
	}
}
