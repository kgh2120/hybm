package com.dragontrain.md.domain.sse.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SseEmitterHolder {

	private final String emitterId;
	private final SseEmitter sseEmitter;


}
