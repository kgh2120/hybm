package com.dragontrain.md.domain.food.infra;


import com.dragontrain.md.domain.food.domain.Food;
import com.dragontrain.md.domain.food.service.port.FoodRepository;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Repository
public class FoodRepositoryImpl implements FoodRepository {
	private final FoodJpaRepository foodJpaRepository;

	@Override
	public List<Food> findAllByRefrigeratorIdAndFoodStorage(Long refrigeratorId, StorageTypeId storageType) {
		return foodJpaRepository.findAllByRefrigerator_RefrigeratorIdAndStorageType_StorageType(refrigeratorId, storageType);
	}

	@Override
	public List<Food> findAllByRefrigeratorId(Long refrigeratorId) {
		return foodJpaRepository.findAllByRefrigerator_RefrigeratorId(refrigeratorId);
	}

	@Override
	public Optional<Food> findById(Long foodId) {
		return foodJpaRepository.findById(foodId);
	}

	@Override
	public void save(Food food) {
		foodJpaRepository.save(food);
	}
}
