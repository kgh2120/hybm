package com.dragontrain.md.domain.sse.controller;

import java.util.Map;

import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.dragontrain.md.domain.refrigerator.event.LevelUp;
import com.dragontrain.md.domain.sse.service.SseService;
import com.dragontrain.md.domain.user.domain.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ServerSentEventHandler {

	private final SseService sseService;


	@GetMapping(value = "/api/sse/connect",  produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter connect(@AuthenticationPrincipal User user){
		return sseService.connect(user);
	}


	@EventListener
	public void handleLevelUpEvent(LevelUp levelUp){
		sseService.sendLevelUpMessage(levelUp.getUserId(), levelUp.getLevel());
	}

}
