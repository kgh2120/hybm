package com.dragontrain.md.domain.food.infra;

import com.dragontrain.md.domain.food.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodJpaRepository extends JpaRepository<Food, Long> {
}
