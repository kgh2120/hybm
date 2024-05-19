package com.dragontrain.md.domain.notice.controller.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class HasnewNoticeResponse {
	private Boolean hasNew;

	public static HasnewNoticeResponse create(Boolean hasNew){
		return HasnewNoticeResponse.builder()
			.hasNew(hasNew)
			.build();
	}
}
