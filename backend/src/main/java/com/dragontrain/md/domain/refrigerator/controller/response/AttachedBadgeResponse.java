package com.dragontrain.md.domain.refrigerator.controller.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class AttachedBadgeResponse {
	private Integer badgeId;
	private String src;
	private Integer position;
}
