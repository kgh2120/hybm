package com.dragontrain.md.domain.statistics.exception;

import com.dragontrain.md.common.config.exception.CustomException;
import com.dragontrain.md.common.config.exception.ErrorCode;

public class StatisticsException extends CustomException {
	public StatisticsException(ErrorCode errorCode) {
		super(errorCode);
	}

	public StatisticsException(ErrorCode errorCode, String loggingMessage) {
		super(errorCode, loggingMessage);
	}
}
