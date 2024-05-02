package com.dragontrain.md.domain.refrigerator.service.port;

import java.util.Optional;

import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;

public interface RefrigeratorRepository {
	Optional<Refrigerator> findByUserId(Long userId);

	void save(Refrigerator refrigerator);
}
