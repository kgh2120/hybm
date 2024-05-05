package com.dragontrain.md.domain.statistics.service.dto;

import com.dragontrain.md.domain.food.domain.CategoryDetail;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class DeletedFoodRank {
	private CategoryDetail categoryDetail;
	private Integer count;
}
