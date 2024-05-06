package com.dragontrain.md.domain.refrigerator.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dragontrain.md.domain.refrigerator.controller.response.MyLevelResponse;
import com.dragontrain.md.domain.refrigerator.domain.Level;
import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorErrorCode;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorException;
import com.dragontrain.md.domain.refrigerator.service.port.RefrigeratorRepository;
import com.dragontrain.md.domain.user.domain.User;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LevelServiceImpl implements LevelService {

	private final RefrigeratorRepository refrigeratorRepository;

	@Override
	public MyLevelResponse getMyLevel(User user) {

		Refrigerator refrigerator = refrigeratorRepository.findByUserId(user.getUserId())
			.orElseThrow(() -> new RefrigeratorException(RefrigeratorErrorCode.REFRIGERATOR_NOT_FOUND));

		Level level = refrigerator.getLevel();

		return MyLevelResponse.of(level.getLevel(), level.getMaxExp(), refrigerator.getExp());
	}
}
