package com.dragontrain.md.domain.food.controller.response;

import com.dragontrain.md.domain.food.domain.Food;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodStorage {
	private Long foodId;
	private String name;
	private String categoryImgSrc;
	private Integer dDay;

	public static FoodStorage create(Long foodId, String name, String categoryImgSrc, Integer dDay) {

		return FoodStorage.builder()
			.foodId(foodId)
			.name(name)
			.categoryImgSrc(categoryImgSrc)
			.dDay(dDay)
			.build();
	}

	public static FoodStorage of(Food food, LocalDate now) {
		return FoodStorage.builder()
			.foodId(food.getFoodId())
			.name(food.getName())
			.categoryImgSrc(food.getCategoryDetail().getImgSrc())
			.dDay(food.getDDay(food.getExpectedExpirationDate(), now))
			.build();
	}
}
