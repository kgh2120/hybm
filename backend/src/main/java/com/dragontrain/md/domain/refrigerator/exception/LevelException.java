package com.dragontrain.md.domain.refrigerator.exception;

import com.dragontrain.md.common.config.exception.CustomException;
import com.dragontrain.md.common.config.exception.ErrorCode;

public class LevelException extends CustomException {
	public LevelException(ErrorCode errorCode) {
		super(errorCode);
	}

	public LevelException(ErrorCode errorCode, String loggingMessage) {
		super(errorCode, loggingMessage);
	}
}
