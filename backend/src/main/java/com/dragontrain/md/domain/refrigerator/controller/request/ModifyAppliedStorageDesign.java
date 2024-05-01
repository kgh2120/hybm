package com.dragontrain.md.domain.refrigerator.controller.request;

import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ModifyAppliedStorageDesign {
	private StorageTypeId position;
	private Integer designId;
}
