package com.dragontrain.md.domain.sse.service.port;

import java.util.Optional;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseRepository {
	void save(Long userId, SseEmitter sseEmitter);

	Optional<SseEmitter> findByUserId(Long userId);

	void deleteEmitterByUserId(Long userId);
}
