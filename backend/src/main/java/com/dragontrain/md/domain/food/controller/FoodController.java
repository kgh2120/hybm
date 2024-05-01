package com.dragontrain.md.domain.food.controller;

import com.dragontrain.md.domain.food.controller.request.ReceiptRequest;
import com.dragontrain.md.domain.food.controller.response.ReceiptProducts;
import com.dragontrain.md.domain.food.service.FoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/food")
public class FoodController {

	private final FoodService foodService;

	@PostMapping("/getGeneralOCR")
	public ResponseEntity<String> getGeneralOCR(@RequestBody String imgURL) {


		return ResponseEntity.ok(foodService.callGeneralOCR(imgURL));
	}

	@PostMapping(value = "/getReceiptOCR", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<ReceiptProducts> getReceiptOCR(@RequestPart("image") MultipartFile imgFile) {


		return ResponseEntity.ok(foodService.callDocumentOCR(imgFile));
	}

	public ResponseEntity<Void> registerReceipt(@RequestBody ReceiptRequest receiptRequest) {

		return ResponseEntity.ok(foodService.registerReceipt(receiptRequest));
	}
}
