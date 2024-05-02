package com.dragontrain.md.domain.user.exception;

import org.springframework.http.HttpStatus;

import com.dragontrain.md.common.config.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserErrorCode implements ErrorCode {

	OAUTH2_EXCEPTION(HttpStatus.BAD_REQUEST, "소셜로그인 과정에서 오류가 발생했습니다."),
	OAUTH2_REDIRECT_URL_MISSING(HttpStatus.BAD_REQUEST, "소셜 로그인 후 REDIRECT_URL이 없습니다."),
	OAUTH2_UNKNOWN_REGISTER_ID(HttpStatus.BAD_REQUEST, "알 수 없는 RegisterId입니다."),
	OAUTH2_EMAIL_DATA_MISSING(HttpStatus.BAD_REQUEST, "OAuth2 벤더로부터 이메일이 전달되지 않았습니다"),
	MISSING_AUTHORIZATION_REQUEST(HttpStatus.BAD_REQUEST, "인증 요청이 만료됐거나, 존재하지 않습니다."),
	USER_RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "유저 정보가 존재하지 않습니다");

	private final HttpStatus httpStatus;
	private final String errorMessage;

	public String getErrorName() {
		return this.name();
	}
}
