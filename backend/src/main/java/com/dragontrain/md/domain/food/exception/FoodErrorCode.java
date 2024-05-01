package com.dragontrain.md.domain.food.exception;

import org.springframework.http.HttpStatus;

import com.dragontrain.md.common.config.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum FoodErrorCode implements ErrorCode {

	UNKNOWN_BARCODE(HttpStatus.NOT_FOUND, "알 수 없는 바코드입니다. 직접 정보를 등록해주세요"),

	UNKNOWN_KAN_CODE(HttpStatus.NOT_FOUND, "알 수 없는 칸코드입니다"),


	;

	private final HttpStatus httpStatus;
	private final String errorMessage;

	public String getErrorName() {
		return this.name();
	}
}
