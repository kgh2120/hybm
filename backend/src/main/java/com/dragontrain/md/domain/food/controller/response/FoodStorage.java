package com.dragontrain.md.domain.food.controller.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
