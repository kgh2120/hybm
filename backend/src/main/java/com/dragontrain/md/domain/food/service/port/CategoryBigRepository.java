package com.dragontrain.md.domain.food.service.port;

import com.dragontrain.md.domain.food.domain.CategoryBig;
import com.dragontrain.md.domain.statistics.service.dto.BigCategoryPriceInfo;

import java.util.List;

public interface CategoryBigRepository {

	List<CategoryBig> findAll();

	List<BigCategoryPriceInfo> findAllBigGroupAndSpend(Long refrigeratorId, Integer year, Integer month);
}
