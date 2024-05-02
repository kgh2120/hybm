package com.dragontrain.md.domain.food.controller.response;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryInfoResponse {
	private Integer categoryBigId;
	private String name;
	private String bigCategoryImgSrc;
	private List<CategoryInfoDetail> categoryDetails;

	public static CategoryInfoResponse create(Integer categoryBigId,
											  String name,
											  String bigCategoryImgSrc,
											  List<CategoryInfoDetail> categoryDetails) {
		return CategoryInfoResponse.builder()
			.categoryBigId(categoryBigId)
			.name(name)
			.bigCategoryImgSrc(bigCategoryImgSrc)
			.categoryDetails(categoryDetails).build();
	}
}
