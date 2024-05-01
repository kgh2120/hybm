package com.dragontrain.md.domain.refrigerator.exception;

import com.dragontrain.md.common.config.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum StorageDesignErrorCode implements ErrorCode {
	REQUEST_POSITION_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 저장고를 찾을 수 없습니다."),
	DUPLICATED_POSITION(HttpStatus.BAD_REQUEST, "각 저장고는 하나의 디자인만 설정할 수 있습니다."),
	DESIGN_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 디자인을 찾지 못했습니다."),
	DESIGN_AND_POSITION_NOT_MATCHED(HttpStatus.BAD_REQUEST, "디자인과 저장고 분류의 매칭이 알맞지 않습니다.")
	;

	private final HttpStatus httpStatus;
	private final String errorMessage;

	public String getErrorName() {
		return this.name();
	}
}
