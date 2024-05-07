package com.dragontrain.md.domain.refrigerator.controller.response;

import com.dragontrain.md.domain.refrigerator.domain.Badge;
import com.dragontrain.md.domain.refrigerator.domain.RefrigeratorBadge;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class BadgeResponse {
	private List<BadgeInfo> has;
	private List<BadgeInfo> hasnot;

	public static BadgeResponse create(List<BadgeInfo> badges) {

		Map<Boolean, List<BadgeInfo>> res = badges.stream()
			.collect(Collectors.groupingBy(BadgeInfo::getIsAttached));

		return BadgeResponse.builder()
			.has(res.get(true))
			.hasnot(res.get(false))
			.build();
	}

}
