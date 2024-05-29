package com.dragontrain.md.domain.refrigerator.controller;

import com.dragontrain.md.domain.refrigerator.event.ExpAcquired;
import com.dragontrain.md.domain.refrigerator.event.GotBadge;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.dragontrain.md.domain.refrigerator.event.LevelUp;
import com.dragontrain.md.domain.refrigerator.service.LevelService;
import com.dragontrain.md.domain.refrigerator.service.RefrigeratorService;
import com.dragontrain.md.domain.refrigerator.service.StorageStorageDesignService;
import com.dragontrain.md.domain.user.event.UserCreated;
import com.dragontrain.md.domain.user.event.UserDeleted;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class RefrigeratorEventHandler {

	private final RefrigeratorService refrigeratorService;
	private final LevelService levelService;
	private final StorageStorageDesignService storageStorageDesignService;

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
		refrigeratorService.gotBadge(gotBadge.getUserId(), gotBadge.getCategoryBigId());
	}

	@TransactionalEventListener
	public void handleExpAddedEvent(ExpAcquired expAcquired) {
		levelService.acquireExp(expAcquired.getUserId(), expAcquired.getExp());
	}

	@TransactionalEventListener
	public void handleLevelUpEvent(LevelUp levelUp) {
		storageStorageDesignService.acquireNewStorageDesign(levelUp.getUserId(), levelUp.getOriginalLevel(),  levelUp.getAfterLevel());
	}

}
