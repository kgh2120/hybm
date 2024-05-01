package com.dragontrain.md.domain.refrigerator.exception;

import com.dragontrain.md.common.config.exception.CustomException;
import com.dragontrain.md.common.config.exception.ErrorCode;

public class StorageDesignException extends CustomException {
	public StorageDesignException(ErrorCode errorCode) {
		super(errorCode);
	}

	public StorageDesignException(ErrorCode errorCode, String loggingMessage) {
		super(errorCode, loggingMessage);
	}
}
