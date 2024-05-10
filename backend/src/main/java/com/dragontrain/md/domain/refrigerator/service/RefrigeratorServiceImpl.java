package com.dragontrain.md.domain.refrigerator.service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.dragontrain.md.common.config.properties.ExpProperties;
import com.dragontrain.md.common.service.EventPublisher;
import com.dragontrain.md.domain.food.domain.CategoryBig;
import com.dragontrain.md.domain.refrigerator.controller.request.BadgeRequest;
import com.dragontrain.md.domain.refrigerator.controller.response.AttachedBadgeResponse;
import com.dragontrain.md.domain.refrigerator.controller.response.BadgeInfo;
import com.dragontrain.md.domain.refrigerator.controller.response.BadgeResponse;
import com.dragontrain.md.domain.refrigerator.domain.*;
import com.dragontrain.md.domain.refrigerator.event.ExpAcquired;
import com.dragontrain.md.domain.refrigerator.service.port.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dragontrain.md.common.service.TimeService;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorErrorCode;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorException;
import com.dragontrain.md.domain.user.domain.User;
import com.dragontrain.md.domain.user.exception.UserErrorCode;
import com.dragontrain.md.domain.user.exception.UserException;
import com.dragontrain.md.domain.user.service.UserRepository;

import lombok.RequiredArgsConstructor;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RefrigeratorServiceImpl
	implements RefrigeratorService {

	private final LevelRepository levelRepository;
	private final UserRepository userRepository;
	private final RefrigeratorRepository refrigeratorRepository;
	private final RefrigeratorBadgeRepository refrigeratorBadgeRepository;
	private final StorageDesignRepository storageDesignRepository;
	private final StorageStorageDesignRepository storageStorageDesignRepository;
	private final BadgeRepository badgeRepository;
	private final TimeService timeService;
	private final EventPublisher eventPublisher;
	private final ExpProperties expProperties;

	@Transactional
	@Override
	public void createInitialRefrigerator(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserException(UserErrorCode.USER_RESOURCE_NOT_FOUND,
				"user id " + userId + " is not founded"));

		if (user.isDeleted()) {
			throw new UserException(UserErrorCode.ACCESS_DELETED_USER);
		}

		registerDefaultStorageDesign(saveRefrigerator(user));
	}

	@Override
	public BadgeResponse getBadges(User user) {

		Refrigerator refrigerator = refrigeratorRepository.findByUserId(user.getUserId())
			.orElseThrow(() -> new RefrigeratorException(RefrigeratorErrorCode.REFRIGERATOR_NOT_FOUND));



		List<BadgeInfo> refrigeratorBadges = refrigeratorBadgeRepository.findAllByRefrigeratorId(
			refrigerator.getRefrigeratorId()
		);

		// List<BadgeInfo> badgeInfos = refrigeratorBadges.stream()
		// 	.map(BadgeInfo::create).toList();

		return BadgeResponse.create(refrigeratorBadges);
	}

	@Transactional
	@Override
	public void switchBadges(List<BadgeRequest> badgeRequests, User user) {
		Long refrigeratorId = refrigeratorRepository.findByUserId(user.getUserId())
			.orElseThrow(() -> new RefrigeratorException(RefrigeratorErrorCode.REFRIGERATOR_NOT_FOUND))
			.getRefrigeratorId();

		int len = badgeRequests.size();
		Set<Integer> badgeIds = new HashSet<>();
		Set<Integer> positions = new HashSet<>();
		badgeRequests.forEach(badgeRequest -> {
			badgeIds.add(badgeRequest.getBadgeId());
			positions.add(badgeRequest.getPosition());
		});
		// BadgeId, position 중복값 검사
		if ((badgeIds.size() != len) || (positions.size() != len)) {
			throw new RefrigeratorException(RefrigeratorErrorCode.INVALID_BADGE_REQUEST);
		}
		// 중복값 1~8이 아닌 경우 검사
		badgeRequests.forEach(badgeRequest -> {
			if (badgeRequest.getPosition() < 1 || badgeRequest.getPosition() > 8) {
				throw new RefrigeratorException(RefrigeratorErrorCode.INVALID_BADGE_POSITION);
			}
			refrigeratorBadgeRepository.findByPosition(refrigeratorId, badgeRequest.getPosition())
				.ifPresent (ob -> {
					ob.detachBadge();
					refrigeratorBadgeRepository.save(ob);
			});
			RefrigeratorBadge newBadge = refrigeratorBadgeRepository.findByBadgeId(refrigeratorId, badgeRequest.getBadgeId())
				.orElseThrow(() -> new RefrigeratorException(RefrigeratorErrorCode.BADGE_NOT_FOUND));
			newBadge.attachBadge(badgeRequest.getPosition());
			refrigeratorBadgeRepository.save(newBadge);
		});
	}

	@Override
	public List<AttachedBadgeResponse> getAttachedBadges(User user) {
		List<RefrigeratorBadge> attachedBadges = refrigeratorBadgeRepository.findAllAttachedBadges(
			refrigeratorRepository.findByUserId(user.getUserId())
				.orElseThrow(() -> new RefrigeratorException(RefrigeratorErrorCode.REFRIGERATOR_NOT_FOUND))
				.getRefrigeratorId()
		);
		return attachedBadges.stream().map(AttachedBadgeResponse::create).toList();
	}

	@Transactional
	@Override
	public void gotBadge(Long userId, Integer categoryBigId) {
		Badge badge = badgeRepository.findBadgeByCategoryBigId(categoryBigId)
			.orElseThrow(() -> new RefrigeratorException(RefrigeratorErrorCode.BADGE_NOT_FOUND));
		if (refrigeratorBadgeRepository.existsByBadgeId(userId, badge.getBadgeId())) {
			return;
		}

		RefrigeratorBadge refrigeratorBadge = RefrigeratorBadge.create(
			refrigeratorRepository.findByUserId(userId)
				.orElseThrow(() -> new RefrigeratorException(RefrigeratorErrorCode.REFRIGERATOR_NOT_FOUND)),
			badge,
			timeService.localDateTimeNow()
		);
		refrigeratorBadgeRepository.save(refrigeratorBadge);
		eventPublisher.publish(new ExpAcquired(userId, expProperties.getGotBadgeAmount()));
	}

	@Transactional
	@Override
	public void deleteRefrigerator(Long userId) {

		Refrigerator refrigerator = refrigeratorRepository.findByUserId(userId)
			.orElseThrow(() -> new RefrigeratorException(RefrigeratorErrorCode.REFRIGERATOR_NOT_FOUND));

		refrigerator.delete(timeService.localDateTimeNow());
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
		).collect(toList());
	}
}
