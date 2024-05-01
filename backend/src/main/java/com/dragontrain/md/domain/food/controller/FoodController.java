package com.dragontrain.md.domain.food.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dragontrain.md.domain.food.controller.request.FoodRegister;
import com.dragontrain.md.domain.food.controller.response.BarcodeInfo;
import com.dragontrain.md.domain.food.controller.response.ExpectedExpirationDate;
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

	@GetMapping("/expiration")
	public ResponseEntity<ExpectedExpirationDate> getExpectedExpirationDate(@RequestParam int year,
		@RequestParam int month, @RequestParam int day,
		@RequestParam int categoryDetailId) {
		return ResponseEntity.ok(foodService.getExpectedExpirationDate(categoryDetailId, year, month, day));
	}

	@PostMapping
	public ResponseEntity<Void> registerFood(@Validated @RequestBody FoodRegister request,
		@AuthenticationPrincipal User user) {
		log.info("req : {}", request);
		foodService.registerFood(request, user);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}
