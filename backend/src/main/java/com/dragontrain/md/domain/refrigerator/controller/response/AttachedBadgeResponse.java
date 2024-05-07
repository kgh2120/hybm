package com.dragontrain.md.domain.refrigerator.controller.response;

import com.dragontrain.md.domain.refrigerator.domain.RefrigeratorBadge;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class AttachedBadgeResponse {
	private Integer badgeId;
	private String src;
	private Integer position;

	public static AttachedBadgeResponse create(RefrigeratorBadge refrigeratorBadge) {
		return AttachedBadgeResponse.builder()
			.badgeId(refrigeratorBadge.getRefrigeratorBadgeId().getBadgeId())
			.src(refrigeratorBadge.getBadge().getImgSrc())
			.position(refrigeratorBadge.getPosition())
			.build();
	}
}
