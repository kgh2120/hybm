package com.dragontrain.md.domain.statistics.controller.response;

import com.dragontrain.md.domain.statistics.service.dto.TopEatenWithCount;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class TopEaten {
	private Integer detailCategoryId;
	private String name;
	private String imgSrc;

	public static TopEaten createByTopEatenWithCount(TopEatenWithCount topEatenWithCount) {
		return TopEaten.builder()
			.detailCategoryId(topEatenWithCount.getCategoryDetailId())
			.name(topEatenWithCount.getName())
			.imgSrc(topEatenWithCount.getImgSrc())
			.build();
	}
}
