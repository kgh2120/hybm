package com.dragontrain.md.domain.refrigerator.infra;

import org.springframework.stereotype.Repository;

import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;
import com.dragontrain.md.domain.refrigerator.service.RefrigeratorRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class RefrigeratorRepositoryImpl implements RefrigeratorRepository {
	private final JpaRefrigeratorRepository jpaRefrigeratorRepository;
	@Override
	public void save(Refrigerator refrigerator) {
		jpaRefrigeratorRepository.save(refrigerator);
	}
}
