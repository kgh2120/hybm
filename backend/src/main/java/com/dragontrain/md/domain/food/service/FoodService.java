package com.dragontrain.md.domain.food.service;

import com.dragontrain.md.domain.food.controller.response.BarcodeInfo;

public interface FoodService {

	BarcodeInfo getBarcodeInfo(Long barcode);
}
