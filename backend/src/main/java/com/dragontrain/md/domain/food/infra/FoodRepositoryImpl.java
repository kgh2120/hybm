package com.dragontrain.md.domain.food.infra;

import com.dragontrain.md.domain.food.domain.Food;
import com.dragontrain.md.domain.food.service.port.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class FoodRepositoryImpl implements FoodRepository {
	private final FoodJpaRepository foodJpaRepository;

	@Override
	public void save(Food food) {
		foodJpaRepository.save(food);
	}
}
