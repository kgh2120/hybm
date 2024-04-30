package com.dragontrain.md.domain.user.exception;

import com.dragontrain.md.common.config.exception.CustomException;
import com.dragontrain.md.common.config.exception.ErrorCode;

public class UserException extends CustomException {
	public UserException(ErrorCode errorCode) {
		super(errorCode);
	}

	public UserException(ErrorCode errorCode, String loggingMessage) {
		super(errorCode, loggingMessage);
	}
}

