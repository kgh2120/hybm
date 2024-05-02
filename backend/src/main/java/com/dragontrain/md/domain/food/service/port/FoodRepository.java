package com.dragontrain.md.domain.food.service.port;

import com.dragontrain.md.domain.food.domain.Food;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;

import java.util.List;

public interface FoodRepository {

	List<Food> findAllByRefrigeratorIdAndFoodStorage(Long id, StorageTypeId storageTypeId);
	void save(Food food);
}
