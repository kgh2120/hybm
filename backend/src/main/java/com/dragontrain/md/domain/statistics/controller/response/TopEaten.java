package com.dragontrain.md.domain.statistics.controller.response;

import com.dragontrain.md.domain.statistics.service.dto.TopEatenWithCount;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class TopEaten {
	private Integer foodId;
	private String name;
	private String imgSrc;

	public static TopEaten createByTopEatenWithCount(TopEatenWithCount topEatenWithCount){
		return TopEaten.builder()
			.foodId(topEatenWithCount.getCategoryDetailId())
			.name(topEatenWithCount.getName())
			.imgSrc(topEatenWithCount.getImgSrc())
			.build();
	}
}
