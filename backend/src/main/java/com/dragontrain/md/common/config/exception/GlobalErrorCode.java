package com.dragontrain.md.common.config.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum GlobalErrorCode implements ErrorCode {
	BIND_ERROR(HttpStatus.BAD_REQUEST, "입력 값이 올바른 형식을 따르지 않았습니다."),
	REQUIRED_PARAM_NOT_FOUND(HttpStatus.BAD_REQUEST, "필요한 값이 주어지지 않았습니다"),
	FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없습니다."),

	LOCAL_FILE_UPLOAD_FAILED(
		HttpStatus.INTERNAL_SERVER_ERROR,
		"잠시후 시도해주세요. 계속해서 문제가 발생한 경우 관리자 이메일(goumunity@gmail.com)로 연락주세요."),
	FILE_TYPE_ERROR(HttpStatus.BAD_REQUEST, "파일이 이미지 타입이 아닙니다."),

	INTERNAL_SERVER_ERROR_CODE(
		HttpStatus.INTERNAL_SERVER_ERROR,
		"잠시후 시도해주세요. 계속해서 문제가 발생한 경우 관리자 이메일(goumunity@gmail.com)로 연락주세요."),

	DB_CONNECT_FAIL(
		HttpStatus.SERVICE_UNAVAILABLE,
		"현재 서비스 상태가 고르지 못해 이용에 차질이 발생합니다. 운영자 이메일을 통해 상황을 제보 부탁드립니다."),

	FILE_SIZE_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "업로드하신 파일의 크기가 너무 큽니다. 10MB 아래의 파일로 설정해주세요"),

	UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "로그인 후 접근해주세요"),

	TOKEN_TYPE_MISS_MATCHED(HttpStatus.FORBIDDEN, "토큰의 타입이 맞지 않습니다"),

	TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다"),

	TOKEN_NOT_VERIFIED(HttpStatus.FORBIDDEN, "토큰의 시그내처가 유효하지 않습니다."), // v
	TOKEN_MALFORMED(HttpStatus.FORBIDDEN, "토큰의 형식이 잘못되었습니다. 토큰은 [header].[payload].[secret]의 형식이어야 합니다."), // v
	TOKEN_UNSUPPORTED(HttpStatus.FORBIDDEN, "지원하지 않는 종류의 토큰입니다."),
	TOKEN_INVALID_KEY(HttpStatus.FORBIDDEN, "유효하지 않은 키입니다."),

	COOKIE_MISSING(HttpStatus.BAD_REQUEST, "필요한 쿠키 값이 넘어오지 않았습니다."),
	REFRESH_TOKEN_MISSING(HttpStatus.UNAUTHORIZED, "리프래시 토큰이 존재하지 않습니다. 다시 로그인해주세요."),

	;

	private final HttpStatus httpStatus;
	private final String errorMessage;

	public String getErrorName() {
		return this.name();
	}
}
