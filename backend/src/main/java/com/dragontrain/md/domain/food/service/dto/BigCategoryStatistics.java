package com.dragontrain.md.domain.food.service.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class BigCategoryStatistics {
	private String bigCategory;
	private Integer money;
}
