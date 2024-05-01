package com.dragontrain.md.domain.food.controller;

import static com.dragontrain.md.domain.food.exception.FoodErrorCode.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.dragontrain.md.domain.food.controller.response.BarcodeInfo;
import com.dragontrain.md.domain.food.exception.FoodErrorCode;
import com.dragontrain.md.domain.food.exception.FoodException;
import com.dragontrain.md.domain.food.service.FoodService;

@WebMvcTest(controllers = FoodController.class)
class FoodControllerTest {

	@MockBean
	FoodService foodService;

	@InjectMocks
	FoodController foodController;

	@Autowired
	MockMvc mockMvc;

	@WithMockUser
	@DisplayName("바코드 정보 조회 테스트 성공")
	@Test
	void getBarcodeInfoSuccessTest() throws Exception{
	// given
		Long givenBarcode = 10101100L;
		String name = "참이슬 빨간뚜껑";
		int categoryId = 5;
		int categoryBigId = 10;
		BDDMockito.given(foodService.getBarcodeInfo(anyLong()))
			.willReturn(BarcodeInfo.builder()
				.name(name)
				.categoryId(categoryId)
				.categoryBigId(categoryBigId)
				.build());
	// when // then
		mockMvc.perform(get("/api/foods")
			.param("barcode", "10101100"))
			.andExpectAll(
				status().isOk(),
				jsonPath("$.name").value(name),
				jsonPath("$.categoryId").value(categoryId),
				jsonPath("$.categoryBigId").value(categoryBigId)
			)
			.andDo(print());
	}

	@WithMockUser
	@DisplayName("바코드 정보 조회 테스트 실패 - 등록되지 않은 바코드인 경우")
	@Test
	void getBarcodeInfoFailBarcodeUnknownTest() throws Exception{
		// given
		final String givenBarcode = "10101100";
		final String givenPath = "/api/foods";
		final String requestParam = "barcode";
		BDDMockito.given(foodService.getBarcodeInfo(anyLong()))
			.willThrow(new FoodException(UNKNOWN_BARCODE));
		// when // then


		mockMvc.perform(get(givenPath)
				.param(requestParam, givenBarcode))
			.andExpectAll(
				status().isNotFound(),
				jsonPath("$.errorName").value(UNKNOWN_BARCODE.getErrorName()),
				jsonPath("$.errorMessage").value(UNKNOWN_BARCODE.getErrorMessage()),
				jsonPath("$.path").value(givenPath)
			)
			.andDo(print());
	}

	@WithMockUser
	@DisplayName("바코드 정보 조회 테스트 실패 - 알 수 없는 칸코드인 경우")
	@Test
	void getBarcodeInfoFailKanCodeUnknownTest() throws Exception{
		// given
		final String givenBarcode = "10101100";
		final String givenPath = "/api/foods";
		final String requestParam = "barcode";
		BDDMockito.given(foodService.getBarcodeInfo(anyLong()))
			.willThrow(new FoodException(UNKNOWN_KAN_CODE));
		// when // then


		mockMvc.perform(get(givenPath)
				.param(requestParam, givenBarcode))
			.andExpectAll(
				status().isNotFound(),
				jsonPath("$.errorName").value(UNKNOWN_KAN_CODE.getErrorName()),
				jsonPath("$.errorMessage").value(UNKNOWN_KAN_CODE.getErrorMessage()),
				jsonPath("$.path").value(givenPath)
			)
			.andDo(print());
	}

}
