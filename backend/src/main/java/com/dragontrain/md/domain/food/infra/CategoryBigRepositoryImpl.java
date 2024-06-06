package com.dragontrain.md.domain.food.infra;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.dragontrain.md.domain.food.domain.CategoryBig;
import com.dragontrain.md.domain.food.service.port.CategoryBigRepository;
import com.dragontrain.md.domain.statistics.service.dto.BigCategoryPriceInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Repository
public class CategoryBigRepositoryImpl implements CategoryBigRepository {

	private final CategoryBigJpaRepository categoryBigJpaRepository;

	@Override
	public List<CategoryBig> findAll() {
		return categoryBigJpaRepository.findAllCategory();
	}

	@Override
	public List<BigCategoryPriceInfo> findAllBigGroupAndSpend(Long refrigeratorId, Integer year, Integer month) {
		return categoryBigJpaRepository.findAllBigGroupAndSpend(refrigeratorId, year, month);
	}

	@Override
	public Optional<CategoryBig> findById(Integer id) {
		return categoryBigJpaRepository.findById(id);
	}
}
