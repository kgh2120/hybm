package com.dragontrain.md.domain.notice.exception;

import com.dragontrain.md.common.config.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum NoticeErrorCode implements ErrorCode {
	NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "알림을 찾지 못했습니다."),
	NOT_MY_NOTICE(HttpStatus.BAD_REQUEST, "내 알림이 아닙니다."),
	ALREADY_DELETED_NOTICE(HttpStatus.BAD_REQUEST, "이미 삭제된 알림입니다")
	;

	private final HttpStatus httpStatus;
	private final String errorMessage;

	public String getErrorName() {
		return this.name();
	}
}
