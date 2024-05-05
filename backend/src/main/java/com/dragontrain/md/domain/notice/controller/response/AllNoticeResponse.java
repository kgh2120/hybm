package com.dragontrain.md.domain.notice.controller.response;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class AllNoticeResponse {
	private List<NoticeResponse> notice;
	private Boolean hasNext;
}
