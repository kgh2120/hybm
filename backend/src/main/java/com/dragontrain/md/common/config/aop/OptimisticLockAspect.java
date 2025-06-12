package com.dragontrain.md.common.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class OptimisticLockAspect {

	@Around("@annotation(optimisticLock)")
	public Object logExecution(ProceedingJoinPoint joinPoint, OptimisticLock optimisticLock) {
		int retryCount = 0;
		while (retryCount < optimisticLock.retryCount()) {
			try{
				return joinPoint.proceed();
			} catch (ObjectOptimisticLockingFailureException e) {
				try {
					retryCount++;
					Thread.sleep(optimisticLock.threadSleepTime());
				} catch (InterruptedException ex) {
					throw new RuntimeException(ex);
				}
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		}
		log.error("Optimistic Lock Retry 횟수를 초과했습니다.");
		throw new OptimisticLockingFailureException("Optimistic lock failure");
	}
}
