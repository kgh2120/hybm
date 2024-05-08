package com.dragontrain.md.domain.food.controller;

import com.dragontrain.md.domain.food.event.EatenCountRaised;
import com.dragontrain.md.domain.food.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FoodEventHandler {

	private final FoodService foodService;

	@EventListener
	public void handleEatenCountRaisedEvent(EatenCountRaised eatenCountRaised) {
		foodService.raiseEatenCount(eatenCountRaised.getUserId(), eatenCountRaised.getFoodId());
	}
}
