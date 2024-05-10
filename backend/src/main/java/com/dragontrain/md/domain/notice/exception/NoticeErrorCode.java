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
	ALREADY_DELETED_NOTICE(HttpStatus.BAD_REQUEST, "이미 삭제된 알림입니다"),

	NOT_FOUND_FCM_CREDENTIALS_FILE(HttpStatus.NOT_FOUND, "FCM 인증에 필요한 파일을 찾지 못했습니다."),
	CANT_CONVERT_NOTICE_TO_JSON(HttpStatus.BAD_REQUEST, "알림 json 변경 오류가 발생했습니다."),
	;

	private final HttpStatus httpStatus;
	private final String errorMessage;

	public String getErrorName() {
		return this.name();
	}
}
