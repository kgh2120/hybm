package com.dragontrain.md.domain.refrigerator.service.dto;

import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class AppliedStorageDesign {
	private Integer id;
	private String imgSrc;
	private StorageTypeId type;
}
