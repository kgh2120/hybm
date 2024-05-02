package com.dragontrain.md.domain.food.infra;

import org.springframework.stereotype.Repository;

import com.dragontrain.md.domain.food.domain.Food;
import com.dragontrain.md.domain.food.service.FoodRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class FoodRepositoryImpl implements FoodRepository {
	private final FoodJpaRepository foodJpaRepository;

	@Override
	public void save(Food food) {
		foodJpaRepository.save(food);
	}
}
