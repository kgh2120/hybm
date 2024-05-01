package com.dragontrain.md.domain.refrigerator.controller.response;

import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;
import lombok.*;

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
