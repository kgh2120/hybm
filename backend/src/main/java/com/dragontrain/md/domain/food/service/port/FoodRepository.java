package com.dragontrain.md.domain.food.service.port;

import java.util.List;
import java.util.Optional;

import com.dragontrain.md.domain.food.domain.Food;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;

public interface FoodRepository {

	List<Food> findAllByRefrigeratorIdAndFoodStorage(Long refrigeratorId, StorageTypeId storageTypeId);

	Optional<Food> findById(Long foodId);

	void save(Food food);

	List<Food> findAllDeletedFoodByRefrigeratorIdAndTime(Long refrigeratorId, Integer year, Integer month);
}
