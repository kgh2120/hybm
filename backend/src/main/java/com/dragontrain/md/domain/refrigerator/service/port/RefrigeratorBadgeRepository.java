package com.dragontrain.md.domain.refrigerator.service.port;

import com.dragontrain.md.domain.refrigerator.domain.RefrigeratorBadge;

import java.util.List;

public interface RefrigeratorBadgeRepository {

	void save(RefrigeratorBadge refrigeratorBadge);
	List<RefrigeratorBadge> findAllByRefrigeratorId(Long refrigeratorId);
}
