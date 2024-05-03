package com.dragontrain.md.domain.food.infra;

import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dragontrain.md.domain.food.domain.Food;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FoodJpaRepository extends JpaRepository<Food, Long> {

	@Query("SELECT f FROM Food f WHERE f.refrigerator.refrigeratorId = :refrigeratorId" +
			" AND f.storageType.storageType = :storageType" +
	 		" AND f.deletedAt IS NULL")
	List<Food> findAllByRefrigerator_RefrigeratorIdAndStorageType_StorageType(Long refrigeratorId, StorageTypeId storageType);

	@Query("select f from Food f join f.refrigerator r" +
		" where r.refrigeratorId=:refrigeratorId and (f.foodDeleteType='EATEN' or f.foodDeleteType='THROWN')" +
		" and year(f.deletedAt)=:year and month(f.deletedAt)=:month")
	List<Food> findAllDeletedFoodByRefrigeratorIdAndTime(Long refrigeratorId, Integer year, Integer month);

	@Query("SELECT f FROM Food f WHERE f.refrigerator.refrigeratorId = :refrigeratorId" +
			" AND f.foodDeleteType IS NULL")
	List<Food> findAllByRefrigerator_RefrigeratorId(Long refrigeratorId);
}
