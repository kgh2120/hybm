package com.dragontrain.md.common.config.exception;

public class TokenException extends CustomException {
	public TokenException(ErrorCode errorCode) {
		super(errorCode);
	}

	public TokenException(ErrorCode errorCode, String loggingMessage) {
		super(errorCode, loggingMessage);
	}
}
