package com.dragontrain.md.domain.statistics.controller.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class SpendByBigCategory {
	private String bigCategory;
	private Integer spend;

	public static SpendByBigCategory create(String bigCategory, Integer spend) {
		return SpendByBigCategory.builder()
			.bigCategory(bigCategory)
			.spend(spend)
			.build();
	}
}
