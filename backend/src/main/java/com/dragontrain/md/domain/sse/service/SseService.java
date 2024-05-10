package com.dragontrain.md.domain.sse.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.dragontrain.md.domain.user.domain.User;

public interface SseService {

	SseEmitter connect(User user);
	void sendLevelUpMessage(Long userId, Integer level);
	void sendNoticeMessage(Long userId);
}
