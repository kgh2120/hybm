package com.dragontrain.md.domain.food.infra;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dragontrain.md.domain.food.domain.CategoryBig;
import com.dragontrain.md.domain.food.service.port.CategoryBigRepository;
import com.dragontrain.md.domain.statistics.service.dto.BigCategoryPriceInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CategoryBigRepositoryImpl implements CategoryBigRepository {

	private final CategoryBigJpaRepository categoryBigJpaRepository;

	@Override
	public List<CategoryBig> findAll() {

		return categoryBigJpaRepository.findAll();
	}

	@Override
	public List<BigCategoryPriceInfo> findAllBigGroupAndSpend(Long refrigeratorId, Integer year, Integer month) {
		return categoryBigJpaRepository.findAllBigGroupAndSpend(refrigeratorId, year, month);
	}
}
