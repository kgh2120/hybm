package com.dragontrain.md.domain.statistics.service.dto;

import com.dragontrain.md.domain.food.domain.CategoryDetail;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class TopEatenWithCount {
	Integer categoryDetailId;
	String name;
	String imgSrc;
	Integer count;

	public static TopEatenWithCount create(CategoryDetail categoryDetail, Integer count){
		return TopEatenWithCount.builder()
			.categoryDetailId(categoryDetail.getCategoryDetailId())
			.name(categoryDetail.getName())
			.imgSrc(categoryDetail.getImgSrc())
			.count(count)
			.build();
	}
}
