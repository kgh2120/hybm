package com.dragontrain.md.domain.notice.exception;

import com.dragontrain.md.common.config.exception.CustomException;
import com.dragontrain.md.common.config.exception.ErrorCode;
public class NoticeException extends CustomException {
	public NoticeException(ErrorCode errorCode) {
		super(errorCode);
	}

	public NoticeException(ErrorCode errorCode, String loggingMessage) {
		super(errorCode, loggingMessage);
	}
}
