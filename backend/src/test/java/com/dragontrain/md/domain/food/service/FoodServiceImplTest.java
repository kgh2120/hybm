package com.dragontrain.md.domain.food.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dragontrain.md.common.service.TimeService;
import com.dragontrain.md.domain.food.controller.response.BarcodeInfo;
import com.dragontrain.md.domain.food.domain.Barcode;
import com.dragontrain.md.domain.food.domain.CategoryBig;
import com.dragontrain.md.domain.food.domain.CategoryDetail;
import com.dragontrain.md.domain.food.exception.FoodErrorCode;
import com.dragontrain.md.domain.food.exception.FoodException;

@ExtendWith(MockitoExtension.class)
class FoodServiceImplTest {

	@Mock
	CategoryDetailRepository categoryDetailRepository;

	@Mock
	BarcodeRepository barcodeRepository;
	@Mock
	CrawlService crawlService;
	@Mock
	TimeService timeService;

	@InjectMocks
	FoodServiceImpl foodService;

	@DisplayName("바코드 정보 조회 테스트 성공 - 바코드가 이미 등록된 경우")
	@Test
	void getBarcodeInfoSuccessAlreadyRegisteredBarcodeTest() throws Exception {
		// given
		Long givenBarcode = 10101000L;
		int categoryBigId = 10;
		CategoryBig categoryBig = CategoryBig.builder()
			.categoryBigId(categoryBigId)
			.name("축산류")
			.imgSrc("1234")
			.build();
		int categoryDetailId = 10;
		CategoryDetail categoryDetail = CategoryDetail.builder()
			.categoryDetailId(categoryDetailId)
			.imgSrc("1234")
			.name("돼지고기")
			.kanCode(101011)
			.categoryBig(categoryBig)
			.expirationDate(365)
			.build();
		String barcodeName = "참이슬 빨간뚜껑";
		given(barcodeRepository.findByBarcodeId(anyLong()))
			.willReturn(Optional.of(Barcode.builder()
				.name(barcodeName)
				.categoryDetail(categoryDetail)
				.build()));
		// when
		BarcodeInfo sut = foodService.getBarcodeInfo(givenBarcode);
		// then
		SoftAssertions sa = new SoftAssertions();
		sa.assertThat(sut.getName()).isEqualTo(barcodeName);
		sa.assertThat(sut.getCategoryId()).isSameAs(categoryDetailId);
		sa.assertThat(sut.getCategoryBigId()).isSameAs(categoryBigId);
		sa.assertAll();
	}

	@DisplayName("바코드 정보 조회 테스트 성공 - 바코드를 크롤링한 경우")
	@Test
	void getBarcodeInfoSuccessCrawlBarcodeTest() throws Exception {
		// given
		Long givenBarcode = 10101000L;
		int categoryBigId = 10;
		CategoryBig categoryBig = CategoryBig.builder()
			.categoryBigId(categoryBigId)
			.name("축산류")
			.imgSrc("1234")
			.build();
		int categoryDetailId = 10;
		int kanCode = 101011;
		CategoryDetail categoryDetail = CategoryDetail.builder()
			.categoryDetailId(categoryDetailId)
			.imgSrc("1234")
			.name("돼지고기")
			.kanCode(kanCode)
			.categoryBig(categoryBig)
			.expirationDate(365)
			.build();
		String barcodeName = "참이슬 빨간뚜껑";
		Barcode barcode = Barcode.builder()
			.name(barcodeName)
			.categoryDetail(categoryDetail)
			.build();
		given(barcodeRepository.findByBarcodeId(anyLong()))
			.willReturn(Optional.empty());

		given(crawlService.crawlBarcode(anyLong()))
			.willReturn(Optional.of(BarcodeCreate.builder()
				.name(barcodeName)
				.kanCode(kanCode)
				.build()));

		given(categoryDetailRepository.findByKanCode(anyInt()))
			.willReturn(Optional.of(categoryDetail));

		given(timeService.localDateTimeNow())
			.willReturn(LocalDateTime.of(2024,5,1,20,29));
		// when
		BarcodeInfo sut = foodService.getBarcodeInfo(givenBarcode);
		// then
		SoftAssertions sa = new SoftAssertions();
		sa.assertThat(sut.getName()).isEqualTo(barcodeName);
		sa.assertThat(sut.getCategoryId()).isSameAs(categoryDetailId);
		sa.assertThat(sut.getCategoryBigId()).isSameAs(categoryBigId);
		sa.assertAll();
		then(barcodeRepository).should(atLeastOnce()).save(any());
	}


	@DisplayName("바코드 정보 조회 테스트 실패 - 알 수 없는 바코드인 경우")
	@Test
	void getBarcodeInfoFailUnknownBarcodeTest() throws Exception {
		// given
		Long givenBarcode = 10101000L;

		given(barcodeRepository.findByBarcodeId(anyLong()))
			.willReturn(Optional.empty());

		given(crawlService.crawlBarcode(anyLong()))
			.willReturn(Optional.empty());


		// when	// then
		assertThatThrownBy(() -> foodService.getBarcodeInfo(givenBarcode))
			.isInstanceOf(FoodException.class)
				.hasFieldOrPropertyWithValue("errorCode", FoodErrorCode.UNKNOWN_BARCODE);
		then(categoryDetailRepository).should(never()).findByKanCode(anyInt());
		then(barcodeRepository).should(never()).save(any());

	}

	@DisplayName("바코드 정보 조회 테스트 실패 - 알 수 없는 칸코드인 경우")
	@Test
	void getBarcodeInfoFailUnknownKanCodeTest() throws Exception {
		// given
		Long givenBarcode = 10101000L;
		int kanCode = 101011;
		String barcodeName = "참이슬 빨간뚜껑";

		given(barcodeRepository.findByBarcodeId(anyLong()))
			.willReturn(Optional.empty());

		given(crawlService.crawlBarcode(anyLong()))
			.willReturn(Optional.of(BarcodeCreate.builder()
				.name(barcodeName)
				.kanCode(kanCode)
				.build()));

		given(categoryDetailRepository.findByKanCode(anyInt()))
			.willReturn(Optional.empty());


		// when	// then
		assertThatThrownBy(() -> foodService.getBarcodeInfo(givenBarcode))
			.isInstanceOf(FoodException.class)
			.hasFieldOrPropertyWithValue("errorCode", FoodErrorCode.UNKNOWN_KAN_CODE);
		then(barcodeRepository).should(never()).save(any());

	}
}
