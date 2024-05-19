package com.dragontrain.md.domain.statistics.controller.response;

import com.dragontrain.md.domain.statistics.service.dto.TopThrownWithCount;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class TopThrown {
	private Integer detailCategoryId;
	private String name;
	private String imgSrc;

	public static TopThrown createByTopThrownWithCount(TopThrownWithCount topThrownWithCount) {
		return TopThrown.builder()
			.detailCategoryId(topThrownWithCount.getCategoryDetailId())
			.name(topThrownWithCount.getName())
			.imgSrc(topThrownWithCount.getImgSrc())
			.build();
	}
}
