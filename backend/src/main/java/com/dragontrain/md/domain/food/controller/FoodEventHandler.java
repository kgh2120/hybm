package com.dragontrain.md.domain.food.controller;

import com.dragontrain.md.domain.food.event.EatenCountRaised;
import com.dragontrain.md.domain.food.service.FoodService;
import com.dragontrain.md.domain.user.event.UserDeleted;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FoodEventHandler {

	private final FoodService foodService;

	@EventListener
	public void handleEatenCountRaisedEvent(EatenCountRaised eatenCountRaised) {
		foodService.raiseEatenCount(eatenCountRaised.getUserId(), eatenCountRaised.getFoodId());
	}

	@Async
	@EventListener
	public void handleUserDeletedEvent(UserDeleted userDeleted) {
		foodService.clearAllFood(userDeleted.getUserId());
	}
}
