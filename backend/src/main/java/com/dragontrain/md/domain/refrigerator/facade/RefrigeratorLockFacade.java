package com.dragontrain.md.domain.refrigerator.facade;

import com.dragontrain.md.common.config.aop.OptimisticLock;
import com.dragontrain.md.common.lock.LockRepository;
import com.dragontrain.md.domain.refrigerator.service.LevelService;
import com.dragontrain.md.domain.refrigerator.service.RefrigeratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class RefrigeratorLockFacade {

	private final LockRepository lockRepository;
	private final RefrigeratorService refrigeratorService;
	private final LevelService levelService;

	@OptimisticLock
	public void acquireExp(Long userId, Integer exp) {
		levelService.acquireExp(userId, exp);
	}

	public void gotBadge(Long userId, Integer categoryBigId) {
		while (!lockRepository.getLock("gotBadge", userId)) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		try {
			refrigeratorService.gotBadge(userId, categoryBigId);
		} finally {
			lockRepository.releaseLock("gotBadge", userId);
		}
	}

}
