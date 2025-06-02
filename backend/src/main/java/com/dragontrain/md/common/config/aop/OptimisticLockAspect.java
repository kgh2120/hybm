package com.dragontrain.md.common.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class OptimisticLockAspect {

	@Around("@annotation(optimisticLock)")
	public Object logExecution(ProceedingJoinPoint joinPoint, OptimisticLock optimisticLock) {
		while (true) {
			try{
				return joinPoint.proceed();
			} catch (ObjectOptimisticLockingFailureException e) {
				try {
					Thread.sleep(optimisticLock.threadSleepTime());
				} catch (InterruptedException ex) {
					throw new RuntimeException(ex);
				}
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		}
	}
}
