package com.dragontrain.md.common.config.exception;

import java.util.List;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

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

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException methodArgumentNotValidException, HttpServletRequest request) {
		log.warn("MethodArgumentNotValidException 발생!!", methodArgumentNotValidException);
		StringBuilder errorMessages = new StringBuilder();
		methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach(fieldError ->
			errorMessages.append(fieldError.getDefaultMessage()).append("\n")
		);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(ErrorResponse.createErrorResponse(GlobalErrorCode.BIND_ERROR, errorMessages.toString(),
				request.getRequestURI()));
	}

	@ExceptionHandler(MissingRequestCookieException.class)
	public ResponseEntity<ErrorResponse> handleMissingRequestCookieException(
		MissingRequestCookieException missingRequestCookieException, HttpServletRequest request) {
		log.warn("missingRequestCookieException 발생!!", missingRequestCookieException);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(ErrorResponse.createErrorResponse(GlobalErrorCode.COOKIE_MISSING,
				"쿠키 [" + missingRequestCookieException.getCookieName() + "] 의 값이 넘어오지 않았습니다.",
				request.getRequestURI()));
	}

	@ExceptionHandler(HandlerMethodValidationException.class)
	public ResponseEntity<ErrorResponse> handleHandlerMethodValidationException(
		HandlerMethodValidationException handlerMethodValidationException, HttpServletRequest request) {
		log.warn("HandlerMethodValidationException 발생!!", handlerMethodValidationException);

		StringBuilder sb = new StringBuilder();

		List<? extends MessageSourceResolvable> errors = handlerMethodValidationException.getAllErrors();
		for (MessageSourceResolvable e : errors) {
			sb.append(e.getDefaultMessage()).append("\n");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(ErrorResponse.createErrorResponse(GlobalErrorCode.BIND_ERROR, sb.toString(),
				request.getRequestURI()));
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
		MissingServletRequestParameterException missingServletRequestParameterException, HttpServletRequest request) {
		log.warn("MissingServletRequestParameterException 발생!!", missingServletRequestParameterException);

		StringBuilder sb = new StringBuilder();
		String missingParamName = missingServletRequestParameterException.getParameterName();
		sb.append("[").append(missingParamName).append("] 을 전달해주세요.");

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(ErrorResponse.createErrorResponse(GlobalErrorCode.BIND_ERROR, sb.toString(),
				request.getRequestURI()));
	}
}
