package com.dragontrain.md.domain.food.service.port;

import java.util.List;
import java.util.Optional;

import com.dragontrain.md.domain.food.domain.CategoryDetail;

public interface CategoryDetailRepository {

	Optional<CategoryDetail> findByKanCode(int kanCode);

	Optional<CategoryDetail> findById(int categoryDetailId);

	List<CategoryDetail> findAllByCategoryBig(int categoryBigId);
}
