package com.dragontrain.md.common.config.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalErrorExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> handleGlobalException(CustomException exception, HttpServletRequest request) {
		log.debug("Custom Exception Occur FROM  {} , comment : {}", request.getRequestURI(),
			exception.getLoggingMessage(), exception);
		return ResponseEntity.status(exception.getErrorCode().getHttpStatus())
			.body(ErrorResponse.createErrorResponse(exception.getErrorCode(), request.getRequestURI()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleUnhandledExceptionException(Exception unhandledException,
																		   HttpServletRequest request) {
		log.error("Unhandled Exception 발생!! FROM {} ", request.getRequestURI(), unhandledException);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(
				ErrorResponse.createErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR_CODE, request.getRequestURI()));
	}

	@ExceptionHandler(MissingRequestHeaderException.class)
	public ResponseEntity<ErrorResponse> handleMissingRequestHeaderException(
		MissingRequestHeaderException missingRequestHeaderException, HttpServletRequest request) {
		log.warn("MissingRequestHeaderException 발생!!", missingRequestHeaderException);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			.body(ErrorResponse.createErrorResponse(GlobalErrorCode.UNAUTHORIZED_ACCESS, request.getRequestURI()));
	}

}
