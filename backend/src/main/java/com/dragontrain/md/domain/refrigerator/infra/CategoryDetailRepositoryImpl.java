package com.dragontrain.md.domain.refrigerator.infra;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.dragontrain.md.domain.food.domain.CategoryDetail;
import com.dragontrain.md.domain.food.infra.CategoryDetailJpaRepository;
import com.dragontrain.md.domain.food.service.port.CategoryDetailRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CategoryDetailRepositoryImpl implements CategoryDetailRepository {
	private final CategoryDetailJpaRepository categoryDetailJpaRepository;

	@Override
	public Optional<CategoryDetail> findByKanCode(int kanCode) {
		return categoryDetailJpaRepository.findByKanCode(kanCode);
	}

	@Override
	public Optional<CategoryDetail> findById(int categoryDetailId) {
		return categoryDetailJpaRepository.findById(categoryDetailId);
	}

	@Override
	public Optional<CategoryDetail> findByName(String name) {
		return categoryDetailJpaRepository.findByName(name);
	}

}
