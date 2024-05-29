package com.dragontrain.md.domain.refrigerator.service.port;

import com.dragontrain.md.domain.refrigerator.domain.RefrigeratorEatenCount;

import java.util.Optional;

public interface RefrigeratorEatenCountRepository {
	Optional<RefrigeratorEatenCount> findByRefrigeratorIdAndCategoryBigId(Long refrigeratorId, Integer categoryBigId);
	void save(RefrigeratorEatenCount refrigeratorEatenCount);
}
