package com.dragontrain.md.domain.refrigerator.controller.response;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class BadgeResponse {
	private List<BadgeInfo> has;
	private List<BadgeInfo> hasnot;

	public static BadgeResponse create(List<BadgeInfo> badges) {

		List<BadgeInfo> has = new ArrayList<>();
		List<BadgeInfo> hasNot = new ArrayList<>();

		for (BadgeInfo badge : badges) {
			if (badge.getIsAttached() != null) {
				has.add(badge);
			} else {
				hasNot.add(badge);
			}
		}

		return BadgeResponse.builder()
			.has(has)
			.hasnot(hasNot)
			.build();
	}

}
