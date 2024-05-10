package com.dragontrain.md.domain.refrigerator.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dragontrain.md.common.service.EventPublisher;
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

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LevelServiceImpl implements LevelService {

	private final RefrigeratorRepository refrigeratorRepository;
	private final LevelRepository levelRepository;
	private final EventPublisher eventPublisher;

	@Override
	public MyLevelResponse getMyLevel(User user) {

		Refrigerator refrigerator = refrigeratorRepository.findByUserId(user.getUserId())
			.orElseThrow(() -> new RefrigeratorException(RefrigeratorErrorCode.REFRIGERATOR_NOT_FOUND));

		Level level = refrigerator.getLevel();

		return MyLevelResponse.of(level.getLevel(), level.getMaxExp(), refrigerator.getExp());
	}

	@Transactional
	@Override
	public void addExp(Long userId, Integer exp) {
		Refrigerator refrigerator = refrigeratorRepository.findByUserId(userId)
			.orElseThrow(() -> new RefrigeratorException(RefrigeratorErrorCode.REFRIGERATOR_NOT_FOUND));

		// refrigerator.plusExp(exp);
		int calculatedExp = refrigerator.getExp() + exp;
		Level level = refrigerator.getLevel();
		int originLevel = level.getLevel();
		while (calculatedExp >= level.getMaxExp() && !level.getLevel().equals(10)) {
			calculatedExp -= level.getMaxExp();
			level = levelRepository.getNextLevel(level.getLevel()).orElseThrow(() -> new LevelException(LevelErrorCode.ALREADY_MAX_LEVEL));
		}
		int afterLevel = level.getLevel();

		refrigerator.acquireExp(calculatedExp, level);

		if (originLevel != afterLevel) {
			// 레벨업 이벤트 쏘기
			eventPublisher.publish(new LevelUp(userId, afterLevel));
		}



	}
}
