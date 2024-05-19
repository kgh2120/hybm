package com.dragontrain.md.domain.refrigerator.service.port;

import com.dragontrain.md.domain.refrigerator.controller.response.BadgeInfo;
import com.dragontrain.md.domain.refrigerator.domain.RefrigeratorBadge;

import java.util.List;
import java.util.Optional;

public interface RefrigeratorBadgeRepository {

	void save(RefrigeratorBadge refrigeratorBadge);
	List<BadgeInfo> findAllByRefrigeratorId(Long refrigeratorId);
	List<RefrigeratorBadge> findAllAttachedBadges(Long refrigeratorId);
	Optional<RefrigeratorBadge> findByBadgeId(Long refrigeratorId, Integer badgeId);
	Optional<RefrigeratorBadge> findByPosition(Long refrigeratorId, Integer position);
	Boolean existsByBadgeId(Long refrigeratorId, Integer badgeId);
}
