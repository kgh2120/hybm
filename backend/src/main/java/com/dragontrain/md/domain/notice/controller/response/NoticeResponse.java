package com.dragontrain.md.domain.notice.controller.response;

import com.dragontrain.md.domain.notice.domain.Notice;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class NoticeResponse {
	private Long noticeId;
	private String content;
	private Boolean isChecked;
	private Long foodId;
	private String foodImgSrc;
	private String createdAt;

	public static NoticeResponse createByNotice(Notice notice){
		return NoticeResponse.builder()
			.noticeId(notice.getNoticeId())
			.content(notice.getContent())
			.isChecked(notice.getIsChecked())
			.foodId(notice.getFood().getFoodId())
			.foodImgSrc(notice.getFood().getCategoryDetail().getImgSrc())
			.createdAt(notice.getCreatedAt().toString())
			.build();
	}
}
