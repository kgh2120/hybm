package com.dragontrain.md.domain.food.service.port;

import com.dragontrain.md.domain.food.domain.CategoryBig;

import java.util.List;

public interface CategoryBigRepository {

	List<CategoryBig> findAll();
}
