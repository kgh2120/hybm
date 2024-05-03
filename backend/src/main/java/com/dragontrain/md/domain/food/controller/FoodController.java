package com.dragontrain.md.domain.food.controller;

import com.dragontrain.md.domain.food.controller.request.FoodInfoRequest;
import com.dragontrain.md.domain.food.controller.response.*;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.dragontrain.md.domain.food.controller.request.FoodRegister;
import com.dragontrain.md.domain.food.controller.response.BarcodeInfo;
import com.dragontrain.md.domain.food.controller.response.ExpectedExpirationDate;
import com.dragontrain.md.domain.food.controller.response.ReceiptProducts;
import com.dragontrain.md.domain.food.service.FoodService;
import com.dragontrain.md.domain.user.domain.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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


	@GetMapping("/category")
	public ResponseEntity<List<CategoryInfoResponse>> getCategoryInfo() {

		return ResponseEntity.ok(foodService.getCategoryInfo());
	}


	@GetMapping("/expiration")
	public ResponseEntity<ExpectedExpirationDate> getExpectedExpirationDate(@RequestParam int year,
		@RequestParam int month, @RequestParam int day,
		@RequestParam int categoryDetailId) {
		return ResponseEntity.ok(foodService.getExpectedExpirationDate(categoryDetailId, year, month, day));
	}

	@PostMapping(value = "/getGeneralOCR", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<String> getGeneralOCR(@RequestPart("image") MultipartFile imgFile) {
		return ResponseEntity.ok(foodService.callGeneralOCR(imgFile));
	}

	@PostMapping(value = "/getReceiptOCR", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<ReceiptProducts> getReceiptOCR(@RequestPart("image") MultipartFile imgFile) {
		return ResponseEntity.ok(foodService.callDocumentOCR(imgFile));
	}

	@PostMapping("/bill")
	public ResponseEntity<Void> registerReceipt(@RequestBody List<FoodInfoRequest> foodInfoRequests,
		@AuthenticationPrincipal User user) {
		foodService.registerReceipt(foodInfoRequests, user);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/storage/{storage}")
	public ResponseEntity<FoodStorageResponse> getFoodStorage(@PathVariable String storage, @AuthenticationPrincipal User user) {


		return ResponseEntity.ok(foodService.getFoodStorage(storage, user));
	}

	@GetMapping("/{foodId}")
	public ResponseEntity<FoodDetailResponse> getFoodDetailInfo(@PathVariable Long foodId) {

		return ResponseEntity.ok(foodService.getFoodDetailInfo(foodId));
	}

	@PutMapping("/{foodId}")
	public ResponseEntity<Void> updateFood(@PathVariable Long foodId,
										   @AuthenticationPrincipal User user,
										   @RequestBody FoodInfoRequest foodInfoRequest) {
		foodService.updateFood(foodId, user, foodInfoRequest);
		return ResponseEntity.ok().build();
	}


	@PostMapping
	public ResponseEntity<Void> registerFood(@Validated @RequestBody FoodRegister request,
		@AuthenticationPrincipal User user) {
		foodService.registerFood(request, user);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}


	@PutMapping("/clear")
	public ResponseEntity<Void> clearRefrigerator(@AuthenticationPrincipal User user) {

		foodService.clearRefrigerator(user);
		return ResponseEntity.ok().build();
	}

}
