package com.dragontrain.md.domain.refrigerator.infra;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;
import com.dragontrain.md.domain.refrigerator.service.port.RefrigeratorRepository;

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
	public Optional<Refrigerator> findByUserId(Long userId) {
		return refrigeratorJpaRepository.findByUser_UserId(userId);
	}

	@Override
	public Optional<Refrigerator> findById(Long refrigeratorId) {
		return refrigeratorJpaRepository.findById(refrigeratorId);
	}
}
