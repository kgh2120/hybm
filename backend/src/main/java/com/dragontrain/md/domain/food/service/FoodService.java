package com.dragontrain.md.domain.food.service;

<<<<<<< backend/src/main/java/com/dragontrain/md/domain/food/service/FoodService.java
import com.dragontrain.md.domain.food.controller.request.ReceiptRequest;
import com.dragontrain.md.domain.food.controller.response.ReceiptProducts;
import com.dragontrain.md.domain.user.domain.User;
import org.springframework.web.multipart.MultipartFile;
=======
import com.dragontrain.md.domain.food.controller.request.FoodRegister;
>>>>>>> backend/src/main/java/com/dragontrain/md/domain/food/service/FoodService.java
import com.dragontrain.md.domain.food.controller.response.BarcodeInfo;
import com.dragontrain.md.domain.food.controller.response.ExpectedExpirationDate;
import com.dragontrain.md.domain.user.domain.User;

public interface FoodService {

	BarcodeInfo getBarcodeInfo(Long barcode);
	ExpectedExpirationDate getExpectedExpirationDate(int categoryDetailId, int year, int month, int day);
	String callGeneralOCR(String imgURL);
	ReceiptProducts callDocumentOCR(MultipartFile imgFile);
	Void registerReceipt(ReceiptRequest receiptRequest, User user);
	void registerFood(FoodRegister request, User user);

}
