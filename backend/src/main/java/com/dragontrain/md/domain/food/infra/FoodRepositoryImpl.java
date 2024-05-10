package com.dragontrain.md.domain.food.infra;

import static com.dragontrain.md.domain.food.domain.QFood.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.dragontrain.md.domain.food.domain.FoodStatus;
import org.springframework.stereotype.Repository;

import com.dragontrain.md.domain.food.domain.Food;
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
				.and(food.deletedAt.isNull()))
			.fetch();
	}

	@Override
	public List<Food> findDangerFoodByRefrigeratorIdAndStorage(Long refrigeratorId, StorageTypeId storageTypeId) {
		return jpaQueryFactory.selectFrom(food)
			.where(food.foodStatus.eq(FoodStatus.DANGER)
				.and(food.refrigerator.refrigeratorId.eq(refrigeratorId))
				.and(food.storageType.storageType.eq(storageTypeId))
				.and(food.deletedAt.isNull()))
			.orderBy(food.expectedExpirationDate.asc())
			.fetch();
	}
}
