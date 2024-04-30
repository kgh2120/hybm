package com.dragontrain.md.domain.refrigerator.infra;

import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;
import com.dragontrain.md.domain.refrigerator.service.port.RefrigeratorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RefregiratorRepositoryimpl implements RefrigeratorRepository {

	private final RefrigeratorJpaRepository refrigeratorJpaRepository;

	@Override
	public Refrigerator findByUserId(Long userId) {
		return refrigeratorJpaRepository.findByUserId(userId);
	}
}
