package com.dragontrain.md.domain.notice.controller.response;

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
}
