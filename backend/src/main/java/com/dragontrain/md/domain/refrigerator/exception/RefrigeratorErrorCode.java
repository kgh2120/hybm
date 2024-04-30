package com.dragontrain.md.domain.refrigerator.exception;

import com.dragontrain.md.common.config.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum RefrigeratorErrorCode implements ErrorCode {
	NOT_FOUND(HttpStatus.NOT_FOUND, "냉장고를 찾을 수 없습니다.")

	;

	private final HttpStatus httpStatus;
	private final String errorMessage;

	public String getErrorName() {
		return this.name();
	}
}
