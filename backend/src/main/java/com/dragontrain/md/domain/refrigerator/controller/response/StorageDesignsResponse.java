package com.dragontrain.md.domain.refrigerator.controller.response;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class StorageDesignsResponse {
	private List<StorageDesignResponse> cool;
	private List<StorageDesignResponse> ice;
	private List<StorageDesignResponse> cabinet;

	public static StorageDesignsResponse createByStorageType(List<StorageDesignResponse> designs) {
		Map<String, List<StorageDesignResponse>> res = designs.stream()
			.collect(Collectors.groupingBy(StorageDesignResponse::getLocation));

		return StorageDesignsResponse.builder()
			.cool(res.get("냉장칸"))
			.ice(res.get("냉동칸"))
			.cabinet(res.get("찬장"))
			.build();
	}
}
