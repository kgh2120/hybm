package com.dragontrain.md.domain.food.service;

import com.dragontrain.md.domain.food.controller.request.ReceiptEachRequest;
import com.dragontrain.md.domain.food.controller.response.FoodStorageResponse;
import com.dragontrain.md.domain.food.controller.response.ReceiptProducts;
import com.dragontrain.md.domain.user.domain.User;
import org.springframework.web.multipart.MultipartFile;
import com.dragontrain.md.domain.food.controller.request.FoodRegister;
import com.dragontrain.md.domain.food.controller.response.BarcodeInfo;
import com.dragontrain.md.domain.food.controller.response.ExpectedExpirationDate;

import java.util.List;

public interface FoodService {

	BarcodeInfo getBarcodeInfo(Long barcode);
	ExpectedExpirationDate getExpectedExpirationDate(int categoryDetailId, int year, int month, int day);
	String callGeneralOCR(String imgURL);
	ReceiptProducts callDocumentOCR(MultipartFile imgFile);
	void registerReceipt(List<ReceiptEachRequest> receiptEachRequests, User user);
	void registerFood(FoodRegister request, User user);
	FoodStorageResponse getFoodStorage(String storage, User user);

}
