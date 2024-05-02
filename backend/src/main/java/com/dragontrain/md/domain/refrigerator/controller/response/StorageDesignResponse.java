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
public class StorageDesignResponse {
	private Integer storageDesignId;
	private String name;
	private String designImgSrc;
	private Boolean isApplied;
	private Integer level;
	private Boolean has;
	private String location;
}
