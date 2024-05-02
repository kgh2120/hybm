package com.dragontrain.md.domain.food.service;

import java.util.Optional;

import com.dragontrain.md.domain.food.domain.CategoryDetail;

public interface CategoryDetailRepository {

	Optional<CategoryDetail> findByKanCode(int kanCode);

	Optional<CategoryDetail> findById(int categoryDetailId);
}
