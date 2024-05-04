package com.dragontrain.md.domain.food.infra;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dragontrain.md.domain.food.domain.Food;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;

public interface FoodJpaRepository extends JpaRepository<Food, Long> {

	List<Food> findAllByRefrigerator_RefrigeratorIdAndStorageType_StorageType(Long refrigeratorId,
		StorageTypeId storageType);

	@Query("select f from Food f join f.refrigerator r" +
		" where r.refrigeratorId=:refrigeratorId and (f.foodDeleteType='EATEN' or f.foodDeleteType='THROWN')" +
		" and year(f.deletedAt)=:year and month(f.deletedAt)=:month")
	List<Food> findAllDeletedFoodByRefrigeratorIdAndTime(Long refrigeratorId, Integer year, Integer month);
}
