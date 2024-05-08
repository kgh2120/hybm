package com.dragontrain.md.domain.food.service.port;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.dragontrain.md.domain.food.domain.Food;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;

public interface FoodRepository {

	List<Food> findAllByRefrigeratorIdAndFoodStorage(Long refrigeratorId, StorageTypeId storageTypeId);

	List<Food> findAllByRefrigeratorId(Long refrigeratorId);

	Optional<Food> findById(Long foodId);

	void save(Food food);

	List<Food> findAllDeletedFoodByRefrigeratorIdAndTime(Long refrigeratorId, Integer year, Integer month);

	List<Food> findFoodByDDay(int dDay, LocalDate now);
}
