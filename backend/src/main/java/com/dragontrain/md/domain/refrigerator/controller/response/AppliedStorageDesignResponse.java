package com.dragontrain.md.domain.refrigerator.controller.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class AppliedStorageDesignResponse {
	private Integer id;
	private String imgSrc;

	public static AppliedStorageDesignResponse createByAppliedStorageDesign(AppliedStorageDesign appliedStorageDesign) {
		return AppliedStorageDesignResponse.builder()
			.id(appliedStorageDesign.getId())
			.imgSrc(appliedStorageDesign.getImgSrc())
			.build();
	}
}
