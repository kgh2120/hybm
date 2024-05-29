package com.dragontrain.md.domain.food.controller.response;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DangerFoodResponse {
	private List<FoodStorage> ICE;
	private List<FoodStorage> COOL;
	private List<FoodStorage> CABINET;
}
