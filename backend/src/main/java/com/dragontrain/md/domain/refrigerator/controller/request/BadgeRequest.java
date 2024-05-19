package com.dragontrain.md.domain.refrigerator.controller.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class BadgeRequest {
	private Integer badgeId;
	private Integer position;
}
