package com.dragontrain.md.domain.food.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dragontrain.md.domain.food.controller.response.BarcodeInfo;
import com.dragontrain.md.domain.food.controller.response.ExpectedExpirationDate;
import com.dragontrain.md.domain.food.service.FoodService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RequestMapping("/api/foods")
@RestController
public class FoodController {

	private final FoodService foodService;

	@GetMapping
	public ResponseEntity<BarcodeInfo> getBarcodeInfo(@RequestParam Long barcode){
		return ResponseEntity.ok(foodService.getBarcodeInfo(barcode));
	}

	@GetMapping("/expiration")
	public ResponseEntity<ExpectedExpirationDate> getExpectedExpirationDate(@RequestParam int year, @RequestParam int month, @RequestParam int day,
		@RequestParam int categoryDetailId){
		return ResponseEntity.ok(foodService.getExpectedExpirationDate(categoryDetailId, year, month, day));
	}

}
