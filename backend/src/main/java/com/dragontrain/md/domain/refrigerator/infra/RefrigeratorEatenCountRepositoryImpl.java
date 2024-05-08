package com.dragontrain.md.domain.refrigerator.infra;

import com.dragontrain.md.domain.refrigerator.domain.RefrigeratorEatenCount;
import com.dragontrain.md.domain.refrigerator.service.port.RefrigeratorEatenCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class RefrigeratorEatenCountRepositoryImpl implements RefrigeratorEatenCountRepository {

	private final RefrigeratorEatenCountJpaRepository refrigeratorEatenCountJpaRepository;

	@Override
	public Optional<RefrigeratorEatenCount> findByRefrigeratorIdAndCategoryBigId(Long refrigeratorId, Integer categoryBigId) {
		return refrigeratorEatenCountJpaRepository.findByRefrigeratorIdAndCategoryBigId(refrigeratorId, categoryBigId);
	}

	@Override
	public void save(RefrigeratorEatenCount refrigeratorEatenCount) {
		refrigeratorEatenCountJpaRepository.save(refrigeratorEatenCount);
	}
}
