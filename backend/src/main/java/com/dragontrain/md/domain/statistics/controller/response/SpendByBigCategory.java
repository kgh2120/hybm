package com.dragontrain.md.domain.statistics.controller.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class SpendByBigCategory {
	private String bigCategory;
	private Integer money;

	public static SpendByBigCategory create(String bigCategory, Integer money){
		return SpendByBigCategory.builder()
			.bigCategory(bigCategory)
			.money(money)
			.build();
	}
}
