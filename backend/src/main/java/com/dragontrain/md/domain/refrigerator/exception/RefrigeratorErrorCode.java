package com.dragontrain.md.domain.refrigerator.exception;

import org.springframework.http.HttpStatus;

import com.dragontrain.md.common.config.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RefrigeratorErrorCode implements ErrorCode {
	NOT_FOUND(HttpStatus.NOT_FOUND, "냉장고를 찾을 수 없습니다."),
	LEVEL_RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "레벨 정보가 존재하지 않습니다"),
	STORAGE_DESIGN_RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "저장고 디자인 정보가 존재하지 않습니다")






	;

	private final HttpStatus httpStatus;
	private final String errorMessage;

	public String getErrorName() {
		return this.name();
	}
}
