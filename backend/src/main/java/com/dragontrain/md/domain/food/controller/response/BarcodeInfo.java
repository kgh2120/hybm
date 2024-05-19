package com.dragontrain.md.domain.food.controller.response;

import com.dragontrain.md.domain.food.domain.Barcode;
import com.dragontrain.md.domain.food.domain.CategoryDetail;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BarcodeInfo {
	private final String name;
	private final Integer categoryId;
	private final Integer categoryBigId;

	public static BarcodeInfo create(Barcode barcode) {
		CategoryDetail categoryDetail = barcode.getCategoryDetail();
		return BarcodeInfo.builder()
			.name(barcode.getName())
			.categoryId(categoryDetail.getCategoryDetailId())
			.categoryBigId(categoryDetail.getCategoryBig().getCategoryBigId())
			.build();
	}
}
