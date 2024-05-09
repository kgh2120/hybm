package com.dragontrain.md.domain.food.service;

import java.util.List;

import com.dragontrain.md.domain.food.controller.response.*;
import org.springframework.web.multipart.MultipartFile;

import com.dragontrain.md.domain.food.controller.request.FoodInfoRequest;
import com.dragontrain.md.domain.food.controller.request.FoodRegister;
import com.dragontrain.md.domain.user.domain.User;

public interface FoodService {

	BarcodeInfo getBarcodeInfo(Long barcode);

	ExpectedExpirationDate getExpectedExpirationDate(int categoryDetailId, int year, int month, int day);

	String callGeneralOCR(MultipartFile imgFile);

	ReceiptProducts callDocumentOCR(MultipartFile imgFile);

	void registerReceipt(List<FoodInfoRequest> foodInfoRequests, User user);

	void registerFood(FoodRegister request, User user);

	FoodStorageResponse getFoodStorage(String storage, User user);

	List<CategoryInfoResponse> getCategoryInfo();

	DangerFoodResponse getDanger(User user);

	FoodDetailResponse getFoodDetailInfo(Long foodId);

	void updateFood(Long foodId, User user, FoodInfoRequest foodInfoRequest);

	void clearRefrigerator(User user);

	void deleteFood(String deleteType, Long[] foodId, User user);

	void raiseEatenCount(Long userId, Long foodId);
}
