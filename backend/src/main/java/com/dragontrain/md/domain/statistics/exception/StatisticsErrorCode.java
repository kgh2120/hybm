package com.dragontrain.md.domain.statistics.exception;

import com.dragontrain.md.common.config.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum StatisticsErrorCode implements ErrorCode {
	NOT_VALIDATED_YEAR_OR_MONTH(HttpStatus.BAD_REQUEST, "연/월 값이 유효하지 않습니다.")

	;
	private final HttpStatus httpStatus;
	private final String errorMessage;

	public String getErrorName() {
		return this.name();
	}
}
