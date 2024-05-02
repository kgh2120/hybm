package com.dragontrain.md.domain.refrigerator.controller.request;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ModifyAppliedStorageDesignRequest {
	private List<ModifyAppliedStorageDesign> request;
}
