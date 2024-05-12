package com.dragontrain.md.domain.refrigerator.exception;

import org.springframework.http.HttpStatus;

import com.dragontrain.md.common.config.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LevelErrorCode implements ErrorCode {
	ALREADY_MAX_LEVEL(HttpStatus.BAD_REQUEST, "이미 최대 레벨입니다."),



	;

	private final HttpStatus httpStatus;
	private final String errorMessage;

	public String getErrorName() {
		return this.name();
	}
}
