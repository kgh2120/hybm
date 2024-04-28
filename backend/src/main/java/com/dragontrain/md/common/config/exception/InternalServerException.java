package com.dragontrain.md.common.config.exception;

import lombok.Getter;

@Getter
public class InternalServerException extends RuntimeException {

	private final String from;

	public InternalServerException(String message, Object clazz) {
		super(message);
		from = clazz.getClass().getName();
	}

	public InternalServerException(String message, Throwable cause, Object clazz) {
		super(message, cause);
		from = clazz.getClass().getName();
	}

	public InternalServerException(Throwable cause, Object clazz) {
		super(cause);
		from = clazz.getClass().getName();
	}
}
