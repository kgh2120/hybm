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
public class TopEatenWithCount {
	private Integer categoryDetailId;
	private String name;
	private String imgSrc;
	private Integer count;

	public static TopEatenWithCount create(CategoryDetail categoryDetail, Integer count) {
		return TopEatenWithCount.builder()
			.categoryDetailId(categoryDetail.getCategoryDetailId())
			.name(categoryDetail.getName())
			.imgSrc(categoryDetail.getImgSrc())
			.count(count)
			.build();
	}
}
