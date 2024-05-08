package com.dragontrain.md.domain.food.infra;

import static com.dragontrain.md.domain.food.domain.QFood.*;

import java.time.LocalDate;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.dragontrain.md.domain.food.domain.Food;
import com.dragontrain.md.domain.food.domain.QFood;
import com.dragontrain.md.domain.food.service.port.FoodRepository;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class FoodRepositoryImpl implements FoodRepository {
	private final FoodJpaRepository foodJpaRepository;
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Food> findAllByRefrigeratorIdAndFoodStorage(Long refrigeratorId, StorageTypeId storageType) {
		return foodJpaRepository.findAllByRefrigerator_RefrigeratorIdAndStorageType_StorageType(refrigeratorId,
			storageType);
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

	@Override
	public List<Food> findAllDeletedFoodByRefrigeratorIdAndTime(Long refrigeratorId, Integer year, Integer month) {
		return foodJpaRepository.findAllDeletedFoodByRefrigeratorIdAndTime(refrigeratorId, year, month);
	}

	@Override
	public List<Food> findFoodByDDay(int dDay, LocalDate now) {
		return jpaQueryFactory.selectFrom(food)
			.where(food.expectedExpirationDate.eq(now.plusDays(dDay))
				.and(food.deletedAt.isNotNull()))
			.fetch();
	}
}
