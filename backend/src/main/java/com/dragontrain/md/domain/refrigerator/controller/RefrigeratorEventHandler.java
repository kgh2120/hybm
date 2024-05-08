package com.dragontrain.md.domain.refrigerator.controller;

import com.dragontrain.md.domain.refrigerator.event.GotBadge;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.dragontrain.md.domain.refrigerator.service.RefrigeratorService;
import com.dragontrain.md.domain.user.event.UserCreated;
import com.dragontrain.md.domain.user.event.UserDeleted;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class RefrigeratorEventHandler {

	private final RefrigeratorService refrigeratorService;

	@EventListener
	public void handleUserCreatedEvent(UserCreated userCreated) {
		refrigeratorService.createInitialRefrigerator(userCreated.getUserId());
	}

	@Async
	@EventListener
	public void handleUserCreatedEvent(UserDeleted userDeleted) {
		refrigeratorService.deleteRefrigerator(userDeleted.getUserId());
	}

	@EventListener
	public void handleGotBadgeEvent(GotBadge gotBadge) {
		refrigeratorService.gotBadge(gotBadge.getRefrigerator(), gotBadge.getCategoryBig());
	}
}
