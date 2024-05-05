package com.dragontrain.md.domain.refrigerator.controller.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MyLevelResponse {

	private final Integer level;
	private final Integer maxExp;
	private final Integer currentExp;

	public static MyLevelResponse of(Integer level, Integer maxExp, Integer currentExp){
		return MyLevelResponse.builder()
			.maxExp(maxExp)
			.level(level)
			.currentExp(currentExp)
			.build();
	}

}
