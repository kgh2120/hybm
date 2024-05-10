package com.dragontrain.md.domain.refrigerator.controller.response;

import com.dragontrain.md.domain.refrigerator.domain.RefrigeratorBadge;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BadgeInfo {
	private Integer badgeId;
	private String name;
	private String badgeImgSrc;
	private String condition;
	private Boolean isAttached;
	private Integer position;



	public static BadgeInfo create(RefrigeratorBadge refrigeratorBadge) {

		return BadgeInfo.builder()
			.badgeId(refrigeratorBadge.getBadge().getBadgeId())
			.name(refrigeratorBadge.getBadge().getBadgeName())
			.badgeImgSrc(refrigeratorBadge.getBadge().getImgSrc())
			.condition(refrigeratorBadge.getBadge().getBadgeRequire())
			.isAttached(refrigeratorBadge.getIsAttached())
			.position(refrigeratorBadge.getPosition())
			.build();
	}
}
