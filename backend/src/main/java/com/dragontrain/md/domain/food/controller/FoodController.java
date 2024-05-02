package com.dragontrain.md.domain.food.controller;

import com.dragontrain.md.domain.food.controller.request.ReceiptEachRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dragontrain.md.domain.food.controller.request.FoodRegister;
import com.dragontrain.md.domain.food.controller.request.ReceiptRequest;
import com.dragontrain.md.domain.food.controller.response.BarcodeInfo;
import com.dragontrain.md.domain.food.controller.response.ExpectedExpirationDate;
import com.dragontrain.md.domain.food.controller.response.ReceiptProducts;
import com.dragontrain.md.domain.food.service.FoodService;
import com.dragontrain.md.domain.user.domain.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/foods")
@RestController
public class FoodController {

	private final FoodService foodService;

	@GetMapping
	public ResponseEntity<BarcodeInfo> getBarcodeInfo(@RequestParam Long barcode) {
			return ResponseEntity.ok(foodService.getBarcodeInfo(barcode));
		}

	@GetMapping("/expiration")
	public ResponseEntity<ExpectedExpirationDate> getExpectedExpirationDate(@RequestParam int year,
																			@RequestParam int month, @RequestParam int day,
																			@RequestParam int categoryDetailId) {

		return ResponseEntity.ok(foodService.getExpectedExpirationDate(categoryDetailId, year, month, day));
	}


	@PostMapping("/getGeneralOCR")
	public ResponseEntity<String> getGeneralOCR(@RequestBody String imgURL) {

		return ResponseEntity.ok(foodService.callGeneralOCR(imgURL));
	}

	@PostMapping(value = "/getReceiptOCR", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<ReceiptProducts> getReceiptOCR(@RequestPart("image") MultipartFile imgFile) {

		return ResponseEntity.ok(foodService.callDocumentOCR(imgFile));
	}

	@PostMapping("/bill")
	public ResponseEntity<Void> registerReceipt(@RequestBody List<ReceiptEachRequest> receiptEachRequests,
												@AuthenticationPrincipal User user) {

		foodService.registerReceipt(receiptEachRequests, user);
		return ResponseEntity.ok().build();
	}



	@PostMapping
	public ResponseEntity<Void> registerFood (@Validated @RequestBody FoodRegister request, @AuthenticationPrincipal User user){
		log.info("req : {}", request);
		foodService.registerFood(request, user);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}
