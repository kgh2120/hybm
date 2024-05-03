package com.dragontrain.md.domain.food.service.port;

import com.dragontrain.md.domain.food.domain.CategoryBig;
import com.dragontrain.md.domain.statistics.service.dto.BigCategoryStatistics;

import java.util.List;

public interface CategoryBigRepository {

	List<CategoryBig> findAll();

	List<BigCategoryStatistics> findAllBigGroupAndSpend(Long refrigeratorId, Integer year, Integer month);
}
