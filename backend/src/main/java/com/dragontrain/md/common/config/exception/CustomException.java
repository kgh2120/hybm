package com.dragontrain.md.common.config.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
	private final ErrorCode errorCode;
	private final String loggingMessage;

	public CustomException(ErrorCode errorCode) {
		super(errorCode.getErrorMessage());
		this.errorCode = errorCode;
		this.loggingMessage = "";
	}


	public CustomException(ErrorCode errorCode, String loggingMessage) {
		super(errorCode.getErrorMessage());
		this.errorCode = errorCode;
		this.loggingMessage = loggingMessage;
	}
}
