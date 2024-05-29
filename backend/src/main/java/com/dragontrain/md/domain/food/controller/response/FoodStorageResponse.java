package com.dragontrain.md.domain.food.controller.response;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodStorageResponse {
	private List<FoodStorage> rotten;
	private List<FoodStorage> danger;
	private List<FoodStorage> warning;
	private List<FoodStorage> fresh;

	public static FoodStorageResponse create(List<FoodStorage> rotten,
		List<FoodStorage> danger,
		List<FoodStorage> warning,
		List<FoodStorage> fresh
		) {

		return FoodStorageResponse.builder()
			.rotten(rotten)
			.danger(danger)
			.warning(warning)
			.fresh(fresh)
			.build();
	}
}
