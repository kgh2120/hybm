package com.dragontrain.md.domain.food.controller.response;

import com.dragontrain.md.domain.food.domain.Food;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodDetailResponse {
	private String name;
	private Integer bigCategoryId;
	private Integer categoryId;
	private String categoryImgSrc;
	private Integer price;
	private String expiredDate;
	private String location;
	private String status;

	public static FoodDetailResponse create(Food food) {
		return FoodDetailResponse.builder()
			.name(food.getName())
			.bigCategoryId(food.getCategoryDetail().getCategoryBig().getCategoryBigId())
			.categoryId(food.getCategoryDetail().getCategoryDetailId())
			.categoryImgSrc(food.getCategoryDetail().getImgSrc())
			.price(food.getPrice())
			.expiredDate(food.getExpectedExpirationDate().toString())
			.location(food.getStorageType().getTypeName())
			.status(food.getFoodStatus().name())
			.build();
	}
}
