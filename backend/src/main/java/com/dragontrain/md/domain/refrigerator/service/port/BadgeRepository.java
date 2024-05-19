package com.dragontrain.md.domain.refrigerator.service.port;

import java.util.Optional;

import com.dragontrain.md.domain.refrigerator.domain.Badge;

public interface BadgeRepository {
	Optional<Badge> findBadgeByCategoryBigId(Integer categoryBigId);
}
