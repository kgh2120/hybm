package com.dragontrain.md.domain.refrigerator.service.port;

import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;

public interface RefrigeratorRepository {
	Refrigerator findByUserId(Long userId);

	void save(Refrigerator refrigerator);
}
