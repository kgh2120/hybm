package com.dragontrain.md.domain.refrigerator.controller.request;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ModifyAppliedStorageDesignRequest {
	private List<ModifyAppliedStorageDesign> request;
}
