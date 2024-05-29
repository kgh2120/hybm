package com.dragontrain.md.domain.refrigerator.controller.response;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;
import com.dragontrain.md.domain.refrigerator.service.dto.AppliedStorageDesign;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class AppliedStorageDesignsResponse {
	private AppliedStorageDesignResponse ice;
	private AppliedStorageDesignResponse cool;
	private AppliedStorageDesignResponse cabinet;

	public static AppliedStorageDesignsResponse createByAppliedStorageDesign(
		List<AppliedStorageDesign> appliedStorageDesigns) {
		Map<StorageTypeId, AppliedStorageDesign> map = appliedStorageDesigns.stream()
			.collect(Collectors.toMap(AppliedStorageDesign::getType, Function.identity()));

		return AppliedStorageDesignsResponse.builder()
			.ice(AppliedStorageDesignResponse.createByAppliedStorageDesign(map.get(StorageTypeId.ICE)))
			.cool(AppliedStorageDesignResponse.createByAppliedStorageDesign(map.get(StorageTypeId.COOL)))
			.cabinet(AppliedStorageDesignResponse.createByAppliedStorageDesign(map.get(StorageTypeId.CABINET)))
			.build();
	}
}
