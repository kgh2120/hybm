package com.dragontrain.md.domain.refrigerator.exception;

import org.springframework.http.HttpStatus;

import com.dragontrain.md.common.config.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RefrigeratorErrorCode implements ErrorCode {
	REFRIGERATOR_NOT_FOUND(HttpStatus.NOT_FOUND, "냉장고를 찾을 수 없습니다."),
	BADGE_NOT_FOUND(HttpStatus.NOT_FOUND, "배지를 찾을 수 없습니다."),
	LEVEL_RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "레벨 정보가 존재하지 않습니다"),
	STORAGE_DESIGN_RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "저장고 디자인 정보가 존재하지 않습니다"),
	INVALID_BADGE_REQUEST(HttpStatus.BAD_REQUEST, "배지ID 또는 위치는 중복된 값이 없어야 합니다"),
	INVALID_BADGE_POSITION(HttpStatus.BAD_REQUEST, "배지 위치는 1이상 8이하의 정수여야 합니다"),
	ALREADY_DELETED_REFRIGERATOR(HttpStatus.FORBIDDEN, "이미 삭제된 냉장고입니다")
	;

	private final HttpStatus httpStatus;
	private final String errorMessage;

	public String getErrorName() {
		return this.name();
	}
}
