package com.dragontrain.md.domain.notice.controller.response;

import com.dragontrain.md.domain.notice.domain.Notice;
import lombok.*;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class AllNoticeResponse {
	private List<NoticeResponse> notice;
	private Boolean hasNext;

	public static AllNoticeResponse create(Slice<Notice> notices){
		return AllNoticeResponse.builder()
			.notice(notices.getContent().stream()
				.map(NoticeResponse::createByNotice)
				.toList())
			.hasNext(notices.hasNext())
			.build();
	}
}
