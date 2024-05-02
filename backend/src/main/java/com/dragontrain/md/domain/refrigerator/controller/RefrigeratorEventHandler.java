package com.dragontrain.md.domain.refrigerator.controller;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.dragontrain.md.domain.refrigerator.service.RefrigeratorService;
import com.dragontrain.md.domain.user.event.UserCreated;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class RefrigeratorEventHandler {

	private final RefrigeratorService refrigeratorService;

	@EventListener
	public void handleUserCreatedEvent(UserCreated userCreated) {
		refrigeratorService.createInitialRefrigerator(userCreated.getUserId());
	}
}
