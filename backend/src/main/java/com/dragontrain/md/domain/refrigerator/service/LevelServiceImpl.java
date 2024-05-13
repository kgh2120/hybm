package com.dragontrain.md.domain.refrigerator.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dragontrain.md.common.service.EventPublisher;
import com.dragontrain.md.common.service.TimeService;
import com.dragontrain.md.domain.refrigerator.controller.response.MyLevelResponse;
import com.dragontrain.md.domain.refrigerator.domain.Level;
import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;
import com.dragontrain.md.domain.refrigerator.event.LevelUp;
import com.dragontrain.md.domain.refrigerator.exception.LevelErrorCode;
import com.dragontrain.md.domain.refrigerator.exception.LevelException;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorErrorCode;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorException;
import com.dragontrain.md.domain.refrigerator.service.port.LevelRepository;
import com.dragontrain.md.domain.refrigerator.service.port.RefrigeratorRepository;
import com.dragontrain.md.domain.user.domain.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LevelServiceImpl implements LevelService {

	private final RefrigeratorRepository refrigeratorRepository;
	private final LevelRepository levelRepository;
	private final EventPublisher eventPublisher;
	private final TimeService timeService;

	@Override
	public MyLevelResponse getMyLevel(User user) {

		Refrigerator refrigerator = refrigeratorRepository.findByUserId(user.getUserId())
			.orElseThrow(() -> new RefrigeratorException(RefrigeratorErrorCode.REFRIGERATOR_NOT_FOUND));

		Level level = refrigerator.getLevel();

		return MyLevelResponse.of(level.getLevel(), level.getMaxExp(), refrigerator.getExp());
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void acquireExp(Long userId, Integer exp) {
		log.debug("acquireExp - userId : {}, exp : {}", userId, exp);
		Refrigerator refrigerator = refrigeratorRepository.findByUserId(userId)
			.orElseThrow(() -> new RefrigeratorException(RefrigeratorErrorCode.REFRIGERATOR_NOT_FOUND));

		int calculatedExp = refrigerator.getExp() + exp;
		Level level = refrigerator.getLevel();
		int originLevel = level.getLevel();

		AcquireExpCalculateResult result = calculateExp(calculatedExp, level);

		calculatedExp = result.getCalculatedExp();
		level = result.getLevel();
		int afterLevel = level.getLevel();

		refrigerator.acquireExp(calculatedExp, level, timeService.localDateTimeNow());

		if (originLevel != afterLevel) {
			eventPublisher.publish(new LevelUp(userId, originLevel,  afterLevel));
		}

		log.debug("acquireExp - finished");
	}

	private AcquireExpCalculateResult calculateExp(int calculatedExp, Level level) {
		while (calculatedExp >= level.getMaxExp() && !level.getLevel().equals(10)) {
			calculatedExp -= level.getMaxExp();
			level = levelRepository.getNextLevel(level.getLevel())
				.orElseThrow(() -> new LevelException(LevelErrorCode.ALREADY_MAX_LEVEL));
		}
		return new AcquireExpCalculateResult(level, calculatedExp);
	}

	@RequiredArgsConstructor
	@Getter
	private static class AcquireExpCalculateResult {
		private final Level level;
		private final int calculatedExp;
	}
}
