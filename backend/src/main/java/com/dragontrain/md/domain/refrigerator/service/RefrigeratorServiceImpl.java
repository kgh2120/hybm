package com.dragontrain.md.domain.refrigerator.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.dragontrain.md.domain.refrigerator.service.port.LevelRepository;
import com.dragontrain.md.domain.refrigerator.service.port.RefrigeratorRepository;
import com.dragontrain.md.domain.refrigerator.service.port.StorageDesignRepository;
import com.dragontrain.md.domain.refrigerator.service.port.StorageStorageDesignRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dragontrain.md.common.service.TimeService;
import com.dragontrain.md.domain.refrigerator.domain.Level;
import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;
import com.dragontrain.md.domain.refrigerator.domain.StorageDesign;
import com.dragontrain.md.domain.refrigerator.domain.StorageStorageDesign;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorErrorCode;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorException;
import com.dragontrain.md.domain.user.domain.User;
import com.dragontrain.md.domain.user.exception.UserErrorCode;
import com.dragontrain.md.domain.user.exception.UserException;
import com.dragontrain.md.domain.user.service.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class RefrigeratorServiceImpl
	implements RefrigeratorService {

	private final LevelRepository levelRepository;
	private final UserRepository userRepository;
	private final RefrigeratorRepository refrigeratorRepository;
	private final StorageDesignRepository storageDesignRepository;
	private final StorageStorageDesignRepository storageStorageDesignRepository;
	private final TimeService timeService;

	@Override
	public void createInitialRefrigerator(Long userId) {
		// userId 가져오기
		// TODO findById에서 isDelete check 해야 함.
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserException(UserErrorCode.USER_RESOURCE_NOT_FOUND,
				"user id " + userId + " is not founded"));
		registerDefaultStorageDesign(saveRefrigerator(user));
	}

	private Refrigerator saveRefrigerator(User user) {
		Refrigerator refrigerator = Refrigerator.create(user, getDefaultLevel(), timeService.localDateTimeNow());
		refrigeratorRepository.save(refrigerator);
		return refrigerator;
	}

	private void registerDefaultStorageDesign(Refrigerator refrigerator) {
		// 각 타입에 해당하는 기본 디자인 가져와서 보유 디자인으로 만들어주기
		LocalDateTime initTime = timeService.localDateTimeNow();
		findDefaultStorageDesign().forEach(sd -> {
			storageStorageDesignRepository.save(StorageStorageDesign.create(refrigerator, sd, initTime));
		});
	}

	private Level getDefaultLevel() {
		return levelRepository.findLevel(1)
			.orElseThrow(() -> new RefrigeratorException(RefrigeratorErrorCode.LEVEL_RESOURCE_NOT_FOUND));
	}

	private List<StorageDesign> findDefaultStorageDesign() {
		return Arrays.stream(StorageTypeId.values()).map(id -> storageDesignRepository
			.findStorageDesignByLevelAndType(1, id)
			.orElseThrow(() -> new RefrigeratorException(RefrigeratorErrorCode.STORAGE_DESIGN_RESOURCE_NOT_FOUND))
		).collect(Collectors.toList());
	}
}
