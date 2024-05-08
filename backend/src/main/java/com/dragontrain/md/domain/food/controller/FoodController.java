package com.dragontrain.md.domain.food.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dragontrain.md.common.config.constraint.Path;
import com.dragontrain.md.domain.food.controller.request.FoodInfoRequest;
import com.dragontrain.md.domain.food.controller.request.FoodRegister;
import com.dragontrain.md.domain.food.controller.response.BarcodeInfo;
import com.dragontrain.md.domain.food.controller.response.CategoryInfoResponse;
import com.dragontrain.md.domain.food.controller.response.ExpectedExpirationDate;
import com.dragontrain.md.domain.food.controller.response.FoodDetailResponse;
import com.dragontrain.md.domain.food.controller.response.FoodStorageResponse;
import com.dragontrain.md.domain.food.controller.response.ReceiptProducts;
import com.dragontrain.md.domain.food.service.FoodService;
import com.dragontrain.md.domain.user.domain.User;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
	public ResponseEntity<FoodStorageResponse> getFoodStorage(@PathVariable String storage,
		@AuthenticationPrincipal User user) {

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

	@DeleteMapping("/{deleteType}")
	public ResponseEntity<Void> deleteFood(
		@PathVariable @Path(candidates = {"eaten", "thrown"}, message = "eaten, thrown의 값만 입력해주세요") String deleteType,
		@RequestParam @NotNull @NotEmpty(message = "foodId를 1개 이상 보내야 합니다.") Long[] foodId,
		@AuthenticationPrincipal User user) {
		foodService.deleteFood(deleteType, foodId, user);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/clear")
	public ResponseEntity<Void> clearRefrigerator(@AuthenticationPrincipal User user) {

		foodService.clearRefrigerator(user);
		return ResponseEntity.ok().build();
	}

}
