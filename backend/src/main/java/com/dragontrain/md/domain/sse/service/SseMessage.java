package com.dragontrain.md.domain.sse.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SseMessage {
	private final Object content;
}
