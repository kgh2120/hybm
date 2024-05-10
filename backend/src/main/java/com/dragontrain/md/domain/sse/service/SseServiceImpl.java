package com.dragontrain.md.domain.sse.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.dragontrain.md.domain.sse.service.port.SseRepository;
import com.dragontrain.md.domain.user.domain.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SseServiceImpl implements SseService {
	private final SseRepository sseRepository;
	private final Long DEFAULT_SSE_EMITTER_TIME_OUT = 10_0000L;

	@Override
	public SseEmitter connect(User user) {

		SseEmitter newSseEmitter = createNewSseEmitter(user.getUserId());
		sseRepository.save(user.getUserId(), newSseEmitter);
		sendDummyMessage(newSseEmitter, user.getUserId());
		return newSseEmitter;
	}

	private SseEmitter createNewSseEmitter(Long userId) {
		SseEmitter sseEmitter = new SseEmitter(DEFAULT_SSE_EMITTER_TIME_OUT);
		sseEmitter.onCompletion(() -> sseRepository.deleteEmitterByUserId(userId)); //완료 시, 타임아웃 시, 에러 발생 시
		sseEmitter.onTimeout(() -> sseRepository.deleteEmitterByUserId(userId));
		sseEmitter.onError((e) -> sseRepository.deleteEmitterByUserId(userId));
		return sseEmitter;
	}

	@Override
	public void sendLevelUpMessage(Long userId, Integer level) {
		sseRepository.findByUserId(userId)
			.ifPresent(sseEmitter -> send(sseEmitter, SseMessageType.LEVEL_UP, userId, level));
	}

	@Override
	public void sendNoticeMessage(Long userId) {
		sseRepository.findByUserId(userId)
			.ifPresent(sseEmitter -> send(sseEmitter, SseMessageType.LEVEL_UP, userId));
	}

	private void sendDummyMessage(SseEmitter sseEmitter, Long userId) {
		send(sseEmitter, SseMessageType.INIT, userId, null);
	}

	private void send(SseEmitter sseEmitter, SseMessageType messageType, Long userId) {
		try {
			sseEmitter.send(SseEmitter.event()
				.id(UUID.randomUUID().toString())
				.name(messageType.name()));
		} catch (IOException exception) {
			sseRepository.deleteEmitterByUserId(userId);
			sseEmitter.completeWithError(exception);
		}
	}

	private void send(SseEmitter sseEmitter, SseMessageType messageType, Long userId, Object data) {
		try {
			sseEmitter.send(SseEmitter.event()
				.id(UUID.randomUUID().toString())
				.name(messageType.name())
				.data(new SseMessage(data), MediaType.APPLICATION_JSON));
		} catch (IOException exception) {
			sseRepository.deleteEmitterByUserId(userId);
			sseEmitter.completeWithError(exception);
		}
	}
}
