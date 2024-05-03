package com.dragontrain.md.domain.statistics.service.dto;

import com.dragontrain.md.domain.food.domain.CategoryDetail;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class TopThrownWithCount {
	private Integer categoryDetailId;
	private String name;
	private String imgSrc;
	private Integer count;

	public static TopThrownWithCount create(CategoryDetail categoryDetail, Integer count){
		return TopThrownWithCount.builder()
			.categoryDetailId(categoryDetail.getCategoryDetailId())
			.name(categoryDetail.getName())
			.imgSrc(categoryDetail.getImgSrc())
			.count(count)
			.build();
	}

}
