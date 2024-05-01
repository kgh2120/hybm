package com.dragontrain.md.domain.refrigerator.infra;

import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorErrorCode;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorException;
import com.dragontrain.md.domain.refrigerator.service.port.RefrigeratorRepository;
import org.springframework.stereotype.Repository;

import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class RefrigeratorRepositoryImpl implements RefrigeratorRepository {
	private final RefrigeratorJpaRepository refrigeratorJpaRepository;
	@Override
	public void save(Refrigerator refrigerator) {
		refrigeratorJpaRepository.save(refrigerator);
	}

	@Override
	public Refrigerator findByUserId(Long userId) {
		return refrigeratorJpaRepository.findByUserId(userId)
			.orElseThrow(() -> new RefrigeratorException(RefrigeratorErrorCode.NOT_FOUND));
	}
}
