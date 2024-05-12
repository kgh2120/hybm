package com.dragontrain.md.domain.sse.infra;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.dragontrain.md.domain.sse.service.port.SseRepository;

@Repository
public class SseRepositoryImpl implements SseRepository {


	private final Map<Long, SseEmitter> storage;

	public SseRepositoryImpl() {
		this.storage = new ConcurrentHashMap<>();
	}

	@Override
	public void save(Long userId, SseEmitter sseEmitter) {
		storage.put(userId, sseEmitter);
	}

	@Override
	public Optional<SseEmitter> findByUserId(Long userId) {
		return Optional.of(storage.get(userId));
	}

	@Override
	public void deleteEmitterByUserId(Long userId) {
		storage.remove(userId);
	}
}
