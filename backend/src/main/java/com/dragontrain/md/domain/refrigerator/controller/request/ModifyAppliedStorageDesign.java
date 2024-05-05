package com.dragontrain.md.domain.refrigerator.controller.request;

import com.dragontrain.md.common.config.constraint.EnumType;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ModifyAppliedStorageDesign {
	@NotNull(message = "저장고 위치를 입력해주세요.")
	@EnumType(targetEnum = StorageTypeId.class, message = "ice, cool, cabinet중 하나를 입력해주세요.")
	private String position;
	@NotNull(message = "적용할 디자인 아이디를 입력해주세요.")
	private Integer designId;
}
