package com.dragontrain.md.domain.statistics.service.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class TopThrownWithCount {
	Integer categoryDetailId;
	String name;
	String imgSrc;
	Integer count;

}
