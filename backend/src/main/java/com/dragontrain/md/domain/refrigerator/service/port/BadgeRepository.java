package com.dragontrain.md.domain.refrigerator.service.port;

import com.dragontrain.md.domain.refrigerator.domain.Badge;

import java.util.Optional;

public interface BadgeRepository {
	Optional<Badge> findBadgeByCategoryBigId(Integer categoryBigId);
}
