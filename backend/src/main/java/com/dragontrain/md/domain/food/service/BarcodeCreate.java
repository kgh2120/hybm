package com.dragontrain.md.domain.food.service;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BarcodeCreate {

	private final Long barcode;
	private final String name;
	private final Integer kanCode;

	public static BarcodeCreate create(Long barcode, String name, Integer kanCode) {
		return BarcodeCreate.builder()
			.barcode(barcode)
			.name(name)
			.kanCode(kanCode)
			.build();
	}
}
