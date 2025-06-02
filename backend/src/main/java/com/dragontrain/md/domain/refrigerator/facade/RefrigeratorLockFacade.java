package com.dragontrain.md.domain.refrigerator.facade;

import com.dragontrain.md.common.lock.LockRepository;
import com.dragontrain.md.domain.refrigerator.service.LevelService;
import com.dragontrain.md.domain.refrigerator.service.RefrigeratorService;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RefrigeratorLockFacade {

	private final LockRepository lockRepository;
	private final RefrigeratorService refrigeratorService;
	private final LevelService levelService;


	public void acquireExp(Long userId, Integer exp){
		while (true) {
			try{
				levelService.acquireExp(userId, exp);
				break;
			} catch (OptimisticLockException | ObjectOptimisticLockingFailureException e) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException ex) {
					throw new RuntimeException(ex);
				}
			}

		}
	}

	public void gotBadge(Long userId, Integer categoryBigId){
		while (!lockRepository.getLock("gotBadge", userId)) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		try{
			refrigeratorService.gotBadge(userId, categoryBigId);
		}finally {
			lockRepository.releaseLock("gotBadge", userId);
		}
	}

}
