package com.dragontrain.md.domain.food.service;

import com.dragontrain.md.domain.food.controller.response.BarcodeInfo;
import com.dragontrain.md.domain.food.controller.response.ExpectedExpirationDate;

public interface FoodService {

	BarcodeInfo getBarcodeInfo(Long barcode);

	ExpectedExpirationDate getExpectedExpirationDate(int categoryDetailId, int year, int month, int day);
}
