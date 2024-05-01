package com.dragontrain.md.domain.refrigerator.exception;

import com.dragontrain.md.common.config.exception.CustomException;
import com.dragontrain.md.common.config.exception.ErrorCode;

public class RefrigeratorException extends CustomException {
	public RefrigeratorException(ErrorCode errorCode) {
		super(errorCode);
	}

	public RefrigeratorException(ErrorCode errorCode, String loggingMessage) {
		super(errorCode, loggingMessage);
	}
}
