package com.dragontrain.md.domain.food.service;

import java.time.DateTimeException;
import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dragontrain.md.common.service.TimeService;
import com.dragontrain.md.domain.food.controller.request.FoodRegister;
import com.dragontrain.md.domain.food.controller.response.BarcodeInfo;
import com.dragontrain.md.domain.food.controller.response.ExpectedExpirationDate;
import com.dragontrain.md.domain.food.domain.Barcode;
import com.dragontrain.md.domain.food.domain.CategoryDetail;
import com.dragontrain.md.domain.food.domain.Food;
import com.dragontrain.md.domain.food.exception.FoodErrorCode;
import com.dragontrain.md.domain.food.exception.FoodException;
import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorErrorCode;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorException;
import com.dragontrain.md.domain.refrigerator.service.port.RefrigeratorRepository;
import com.dragontrain.md.domain.user.domain.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class FoodServiceImpl implements FoodService {

	private final RefrigeratorRepository refrigeratorRepository;
	private final FoodRepository foodRepository;
	private final CategoryDetailRepository categoryDetailRepository;
	private final BarcodeRepository barcodeRepository;
	private final CrawlService crawlService;
	private final TimeService timeService;

	@Override
	public BarcodeInfo getBarcodeInfo(Long barcode) {

		Barcode barcodeInfo = barcodeRepository.findByBarcodeId(barcode)
			.orElseGet(() -> {
				// 검색
				BarcodeCreate barcodeCreate = crawlService.crawlBarcode(barcode)
					.orElseThrow(() -> new FoodException(FoodErrorCode.UNKNOWN_BARCODE));

				CategoryDetail categoryDetail = categoryDetailRepository.findByKanCode(barcodeCreate.getKanCode())
					.orElseThrow(() -> new FoodException(FoodErrorCode.UNKNOWN_KAN_CODE));

				Barcode createdBarcode = Barcode.create(barcodeCreate.getName(), categoryDetail,
					timeService.localDateTimeNow());
				barcodeRepository.save(createdBarcode);
				return createdBarcode;
			});

		return BarcodeInfo.create(barcodeInfo);
	}

	@Override
	public ExpectedExpirationDate getExpectedExpirationDate(int categoryDetailId, int year, int month, int day) {
		// 일단 날짜 만들기
		LocalDate targetDate = makeLocalDate(year, month, day);
		CategoryDetail categoryDetail = categoryDetailRepository.findById(categoryDetailId)
			.orElseThrow(() -> new FoodException(FoodErrorCode.CATEGORY_DETAIL_NOT_FOUND));

		if (categoryDetail.getExpirationDate().equals(0)) {
			throw new FoodException(FoodErrorCode.EXPIRATION_DATE_NOT_FOUND);
		}

		return ExpectedExpirationDate.from(targetDate.plusDays(categoryDetail.getExpirationDate()));
	}

	@Override
	public void registerFood(FoodRegister request, User user) {
		// 유저로 냉장고 찾아오기
		LocalDate expiredDate = makeLocalDate(request.getExpiredDate());
		Refrigerator refrigerator = refrigeratorRepository.findByUserId(user.getUserId())
			.orElseThrow(() -> new RefrigeratorException(RefrigeratorErrorCode.REFRIGERATOR_NOT_FOUND));
		// 저장고 위치 찾아오기
		StorageTypeId storageTypeId = StorageTypeId.valueOf(request.getLocation().toUpperCase());

		CategoryDetail categoryDetail = categoryDetailRepository.findById(request.getCategoryId())
			.orElseThrow(() -> new FoodException(FoodErrorCode.CATEGORY_DETAIL_NOT_FOUND));

		// Food
		foodRepository.save(Food.create(request.getName(), categoryDetail, request.getPrice(), expiredDate,
			storageTypeId, refrigerator, timeService.localDateTimeNow()));
	}

	private LocalDate makeLocalDate(int year, int month, int day) {
		try {
			return LocalDate.of(year, month, day);
		} catch (DateTimeException e) {
			throw new FoodException(FoodErrorCode.INVALID_DATE_FORMAT);
		}
	}

	private LocalDate makeLocalDate(String date) {
		try {
			return LocalDate.parse(date);
		} catch (DateTimeException e) {
			throw new FoodException(FoodErrorCode.INVALID_DATE_FORMAT);
		}
	}
}
