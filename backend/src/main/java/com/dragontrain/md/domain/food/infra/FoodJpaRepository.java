package com.dragontrain.md.domain.food.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dragontrain.md.domain.food.domain.Food;

public interface FoodJpaRepository extends JpaRepository<Food, Long> {
}
