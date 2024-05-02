package com.dragontrain.md.domain.refrigerator.service.port;

import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;

import java.util.Optional;

public interface RefrigeratorRepository {
	Optional<Refrigerator> findByUserId(Long userId);

	void save(Refrigerator refrigerator);
}
