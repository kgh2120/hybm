package com.dragontrain.md.domain.food.controller.response;

import com.dragontrain.md.domain.food.domain.Barcode;
import com.dragontrain.md.domain.food.domain.CategoryDetail;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BarcodeInfo {
	private  String name;
	private  Integer categoryId;
	private  Integer categoryBigId;

	public static BarcodeInfo create(Barcode barcode) {
		CategoryDetail categoryDetail = barcode.getCategoryDetail();
		return BarcodeInfo.builder()
			.name(barcode.getName())
			.categoryId(categoryDetail.getCategoryDetailId())
			.categoryBigId(categoryDetail.getCategoryBig().getCategoryBigId())
			.build();
	}
}
