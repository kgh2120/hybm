package com.dragontrain.md.domain.food.infra;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.dragontrain.md.domain.food.domain.CategoryDetail;
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
	public List<CategoryDetail> findByName(String name) {
		return categoryDetailJpaRepository.findAllByName(name);
	}

	@Override
	public Boolean existsByName(String name) {
		return categoryDetailJpaRepository.existsByName(name);
	}

	@Override
	public Boolean existsByCategoryDetailId(Integer categoryDetailId) {
		return categoryDetailJpaRepository.existsByCategoryDetailId(categoryDetailId);
	}

}
