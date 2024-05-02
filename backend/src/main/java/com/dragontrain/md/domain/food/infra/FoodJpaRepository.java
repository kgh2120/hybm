package com.dragontrain.md.domain.food.infra;

import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dragontrain.md.domain.food.domain.Food;

import java.util.List;
import java.util.Optional;

public interface FoodJpaRepository extends JpaRepository<Food, Long> {

	List<Food> findAllByRefrigerator_RefrigeratorIdAndStorageType_StorageType(Long refrigeratorId, StorageTypeId storageType);
}
