package com.dragontrain.md.domain.food.controller;

import static com.dragontrain.md.domain.food.exception.FoodErrorCode.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.dragontrain.md.common.config.exception.GlobalErrorCode;
import com.dragontrain.md.domain.food.controller.response.BarcodeInfo;
import com.dragontrain.md.domain.food.controller.response.ExpectedExpirationDate;
import com.dragontrain.md.domain.food.exception.FoodException;
import com.dragontrain.md.domain.food.service.FoodService;

@WebMvcTest(controllers = {FoodController.class})
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
	void getBarcodeInfoSuccessTest() throws Exception {
		// given
		Long givenBarcode = 10101100L;
		String name = "참이슬 빨간뚜껑";
		int categoryId = 5;
		int categoryBigId = 10;
		given(foodService.getBarcodeInfo(anyLong()))
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
	void getBarcodeInfoFailBarcodeUnknownTest() throws Exception {
		// given
		final String givenBarcode = "10101100";
		final String givenPath = "/api/foods";
		final String requestParam = "barcode";
		given(foodService.getBarcodeInfo(anyLong()))
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
	void getBarcodeInfoFailKanCodeUnknownTest() throws Exception {
		// given
		final String givenBarcode = "10101100";
		final String givenPath = "/api/foods";
		final String requestParam = "barcode";
		given(foodService.getBarcodeInfo(anyLong()))
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

	@WithMockUser
	@DisplayName("예상 소비기한 조회 테스트 성공")
	@Test
	void getExpectedExpirationDateSuccessTest() throws Exception {
		// given
		int categoryDetailId = 10;
		int year = 2024;
		int month = 5;
		int day = 1;
		final String path = "/api/foods/expiration";
		given(foodService.getExpectedExpirationDate(anyInt(), anyInt(), anyInt(), anyInt()))
			.willReturn(ExpectedExpirationDate.from(LocalDate.of(2024, 5, 15)));
		// when // then
		mockMvc.perform(get(path)
				.param("categoryDetailId", Integer.toString(categoryDetailId))
				.param("year", Integer.toString(year))
				.param("month", Integer.toString(month))
				.param("day", Integer.toString(day))
			).andExpectAll(
				status().isOk(),
				jsonPath("$.year").value(2024),
				jsonPath("$.month").value(5),
				jsonPath("$.day").value(15)
			)
			.andDo(print());
	}

	@WithMockUser
	@DisplayName("예상 소비기한 조회 테스트 실패 - 날짜 형식이 맞지 않는 경우")
	@Test
	void getExpectedExpirationDateFailCurrentDateFormatErrorTest() throws Exception {
		// given
		int categoryDetailId = 10;
		int year = 2024;
		int month = 4;
		int day = 31;
		final String path = "/api/foods/expiration";
		given(foodService.getExpectedExpirationDate(anyInt(), anyInt(), anyInt(), anyInt()))
			.willThrow(new FoodException(INVALID_DATE_FORMAT));
		// when // then
		mockMvc.perform(get(path)
				.param("categoryDetailId", Integer.toString(categoryDetailId))
				.param("year", Integer.toString(year))
				.param("month", Integer.toString(month))
				.param("day", Integer.toString(day))
			).andExpectAll(
				status().isBadRequest(),
				jsonPath("$.errorName").value(INVALID_DATE_FORMAT.getErrorName()),
				jsonPath("$.errorMessage").value(INVALID_DATE_FORMAT.getErrorMessage()),
				jsonPath("$.path").value(path)
			)
			.andDo(print());
	}

	@WithMockUser
	@DisplayName("예상 소비기한 조회 테스트 실패 - 소분류 아이디가 없는 경우")
	@Test
	void getExpectedExpirationDateFailCategoryDetailIdNotFoundTest() throws Exception {
		// given
		int categoryDetailId = 10;
		int year = 2024;
		int month = 5;
		int day = 1;
		final String path = "/api/foods/expiration";
		given(foodService.getExpectedExpirationDate(anyInt(), anyInt(), anyInt(), anyInt()))
			.willThrow(new FoodException(CATEGORY_DETAIL_NOT_FOUND));
		// when // then
		mockMvc.perform(get(path)
				.param("categoryDetailId", Integer.toString(categoryDetailId))
				.param("year", Integer.toString(year))
				.param("month", Integer.toString(month))
				.param("day", Integer.toString(day))
			).andExpectAll(
				status().isNotFound(),
				jsonPath("$.errorName").value(CATEGORY_DETAIL_NOT_FOUND.getErrorName()),
				jsonPath("$.errorMessage").value(CATEGORY_DETAIL_NOT_FOUND.getErrorMessage()),
				jsonPath("$.path").value(path)
			)
			.andDo(print());
	}

	@WithMockUser
	@DisplayName("예상 소비기한 조회 테스트 실패 - 소비 기한 정보가 없는 경우")
	@Test
	void getExpectedExpirationDateFailExpirationNotFoundTest() throws Exception {
		// given
		int categoryDetailId = 10;
		int year = 2024;
		int month = 5;
		int day = 1;
		final String path = "/api/foods/expiration";
		given(foodService.getExpectedExpirationDate(anyInt(), anyInt(), anyInt(), anyInt()))
			.willThrow(new FoodException(EXPIRATION_DATE_NOT_FOUND));
		// when // then
		mockMvc.perform(get(path)
				.param("categoryDetailId", Integer.toString(categoryDetailId))
				.param("year", Integer.toString(year))
				.param("month", Integer.toString(month))
				.param("day", Integer.toString(day))
			).andExpectAll(
				status().isNotFound(),
				jsonPath("$.errorName").value(EXPIRATION_DATE_NOT_FOUND.getErrorName()),
				jsonPath("$.errorMessage").value(EXPIRATION_DATE_NOT_FOUND.getErrorMessage()),
				jsonPath("$.path").value(path)
			)
			.andDo(print());
	}

	@WithMockUser
	@DisplayName("음식 등록 테스트 성공")
	@Test
	void registerFoodSuccessTest() throws Exception {
		// given
		String body = "{\n"
			+ "   \"name\":\"삼겹살\",\n"
			+ "   \"categoryId\":10,\n"
			+ "   \"price\":26000,\n"
			+ "   \"expiredDate\":\"2024-04-26\",\n"
			+ "   \"location\":\"CABINET\"\n"
			+ "}";
		final String url = "/api/foods";
		// when // then
		mockMvc.perform(post(url)
				.content(body)
				.contentType(MediaType.APPLICATION_JSON)
				.with(csrf()))
			.andExpectAll(
				status().isCreated()
			).andDo(print());
	}

	@WithMockUser
	@DisplayName("음식 등록 테스트 실패 - location에 올바르지 못한 값을 입력한 경우")
	@Test
	void registerFoodFailKnownLocationTest() throws Exception {
		// given
		String body = "{\n"
			+ "   \"name\":\"삼겹살\",\n"
			+ "   \"categoryId\":10,\n"
			+ "   \"price\":26000,\n"
			+ "   \"expiredDate\":\"2024-04-26\",\n"
			+ "   \"location\":\"HELLO\"\n"
			+ "}";
		final String url = "/api/foods";
		// when // then
		mockMvc.perform(post(url)
				.content(body)
				.contentType(MediaType.APPLICATION_JSON)
				.with(csrf()))
			.andExpectAll(
				status().isBadRequest(),
				jsonPath("$.errorName").value(GlobalErrorCode.BIND_ERROR.getErrorName()),
				jsonPath("$.errorMessage").value("ice, cool, cabinet에 해당하는 값을 입력해주세요\n"),
				jsonPath("$.path").value(url)
			).andDo(print());
	}

	@WithMockUser
	@DisplayName("음식 등록 테스트 실패 - 카테고리가 없는 경우")
	@Test
	void registerFoodFailCategoryNotFoundTest() throws Exception {
		// given
		String body = "{\n"
			+ "   \"name\":\"삼겹살\",\n"
			+ "   \"categoryId\":6767,\n"
			+ "   \"price\":26000,\n"
			+ "   \"expiredDate\":\"2024-04-26\",\n"
			+ "   \"location\":\"ice\"\n"
			+ "}";
		final String url = "/api/foods";

		willThrow(new FoodException(CATEGORY_DETAIL_NOT_FOUND))
			.given(foodService)
			.registerFood(any(), any());
		// when // then
		mockMvc.perform(post(url)
				.content(body)
				.contentType(MediaType.APPLICATION_JSON)
				.with(csrf()))
			.andExpectAll(
				status().isNotFound(),
				jsonPath("$.errorName").value(CATEGORY_DETAIL_NOT_FOUND.getErrorName()),
				jsonPath("$.errorMessage").value(CATEGORY_DETAIL_NOT_FOUND.getErrorMessage()),
				jsonPath("$.path").value(url)
			).andDo(print());
	}

	@WithMockUser
	@DisplayName("음식 등록 테스트 실패 - 날짜 양식이 안맞는 경우")
	@Test
	void registerFoodFailDateTimeFormatErrorTest() throws Exception {
		// given
		String body = "{\n"
			+ "   \"name\":\"삼겹살\",\n"
			+ "   \"categoryId\":10,\n"
			+ "   \"price\":26000,\n"
			+ "   \"expiredDate\":\"2024-04-31\",\n"
			+ "   \"location\":\"ice\"\n"
			+ "}";
		final String url = "/api/foods";

		willThrow(new FoodException(INVALID_DATE_FORMAT))
			.given(foodService)
			.registerFood(any(), any());

		// when // then
		mockMvc.perform(post(url)
				.content(body)
				.contentType(MediaType.APPLICATION_JSON)
				.with(csrf()))
			.andExpectAll(
				status().isBadRequest(),
				jsonPath("$.errorName").value(INVALID_DATE_FORMAT.getErrorName()),
				jsonPath("$.errorMessage").value(INVALID_DATE_FORMAT.getErrorMessage()),
				jsonPath("$.path").value(url)
			).andDo(print());
	}

	@WithMockUser
	@DisplayName("음식 등록 테스트 실패 - 이름이 양식에 맞지 않는 경우")
	@Test
	void registerFoodFailInvalidNameTest() throws Exception {
		// given
		String body = "{\n"
			+ "   \"categoryId\":10,\n"
			+ "   \"price\":26000,\n"
			+ "   \"expiredDate\":\"2024-04-31\",\n"
			+ "   \"location\":\"asd\"\n"
			+ "}";
		final String url = "/api/foods";

		// when // then
		mockMvc.perform(post(url)
				.content(body)
				.contentType(MediaType.APPLICATION_JSON)
				.with(csrf()))
			.andExpectAll(
				status().isBadRequest(),
				jsonPath("$.errorName").value(GlobalErrorCode.BIND_ERROR.getErrorName()),
				jsonPath("$.path").value(url)
			).andDo(print());
	}
}
