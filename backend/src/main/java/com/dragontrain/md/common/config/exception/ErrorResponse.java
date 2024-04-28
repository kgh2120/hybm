package com.dragontrain.md.common.config.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

	private String timeStamp;
	private HttpStatus httpStatus;
	private String errorName;
	private String errorMessage;
	private String path;

	public static ErrorResponse createErrorResponse(ErrorCode errorCode, String path) {
		return new ErrorResponse(
			Instant.now().toString(),
			errorCode.getHttpStatus(),
			errorCode.getErrorName(),
			errorCode.getErrorMessage(),
			path);
	}

	@Override
	public String toString() {
		return "{"
			+ "timeStamp="
			+ timeStamp
			+ ", httpStatus="
			+ httpStatus
			+ ", errorName='"
			+ errorName
			+ '\''
			+ ", errorMessage='"
			+ errorMessage
			+ '\''
			+ ", path='"
			+ path
			+ '\''
			+ '}';
	}
}
