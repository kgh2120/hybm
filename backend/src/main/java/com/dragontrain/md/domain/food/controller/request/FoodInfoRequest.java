package com.dragontrain.md.domain.food.controller.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodInfoRequest {

	private String name;
	private Integer categoryId;
	private Integer price;
	private String expiredDate;
	private String location;
}

