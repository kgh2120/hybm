package com.dragontrain.md.domain.food.service;

import com.dragontrain.md.domain.food.controller.request.ReceiptEachRequest;
import com.dragontrain.md.domain.food.controller.response.*;
import com.dragontrain.md.domain.food.domain.CategoryBig;
import com.dragontrain.md.domain.food.domain.Food;
import com.dragontrain.md.domain.food.service.port.BarcodeRepository;
import com.dragontrain.md.domain.food.service.port.CategoryBigRepository;
import com.dragontrain.md.domain.food.service.port.CategoryDetailRepository;
import com.dragontrain.md.domain.food.service.port.FoodRepository;
import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;
import com.dragontrain.md.domain.refrigerator.service.port.RefrigeratorRepository;
import com.dragontrain.md.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import java.time.DateTimeException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dragontrain.md.common.service.TimeService;
import com.dragontrain.md.domain.food.controller.request.FoodRegister;
import com.dragontrain.md.domain.food.domain.Barcode;
import com.dragontrain.md.domain.food.domain.CategoryDetail;
import com.dragontrain.md.domain.food.exception.FoodErrorCode;
import com.dragontrain.md.domain.food.exception.FoodException;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorErrorCode;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorException;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodServiceImpl implements FoodService {


	// OCR General 형식의 SECRET key, API URL
	@Value("${secret.ocr.general.service-key}")
	private String OCR_SECRET;
	@Value("${secret.ocr.general.api-url}")
	private String API_URL;

	// OCR DOCUMENT 형식의 SECRET key, API URL
	@Value("${secret.ocr.document.service-key}")
	private String RECEIPT_SECRET;
	@Value("${secret.ocr.document.api-url}")
	private String RECEIPT_API_URL;


	private final RefrigeratorRepository refrigeratorRepository;
	private final FoodRepository foodRepository;
	private final CategoryDetailRepository categoryDetailRepository;
	private final CategoryBigRepository categoryBigRepository;
	private final BarcodeRepository barcodeRepository;
	private final CrawlService crawlService;
	private final TimeService timeService;



	// 이미지로 OCR General을 요청하는 Component
	@Override
	public String callGeneralOCR(String imgURL) {

		try {
			URL url = new URL(API_URL);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setUseCaches(false);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			con.setRequestProperty("X-OCR-SECRET", OCR_SECRET);

			JSONObject json = new JSONObject();
			json.put("version", "V2");
			json.put("requestId", UUID.randomUUID().toString());
			json.put("timestamp", System.currentTimeMillis());
			JSONObject image = new JSONObject();
			image.put("format", "jpg");
			image.put("url", imgURL);

			image.put("name", "demo");
			JSONArray images = new JSONArray();
			images.put(image);
			json.put("images", images);
			String postParams = json.toString();

			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(postParams);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();

			System.out.println(response);
			return response.toString();
		} catch (Exception e) {
			System.out.println(e);
		}
		return "fail";
	}

	@Override
	public ReceiptProducts callDocumentOCR(MultipartFile imgFile) {

		ReceiptProducts receiptProducts = new ReceiptProducts();
		byte[] imgBytes = new byte[0];
		try {
			imgBytes = imgFile.getBytes();
		} catch (Exception e) {
			System.out.println("이미지 Byte 변환 실패 : " + e);
		}

		try {
			URL url = new URL(RECEIPT_API_URL);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setUseCaches(false);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			con.setRequestProperty("X-OCR-SECRET", RECEIPT_SECRET);

			JSONObject json = new JSONObject();
			json.put("version", "V2");
			json.put("requestId", UUID.randomUUID().toString());
			json.put("timestamp", System.currentTimeMillis());
			JSONObject image = new JSONObject();
			image.put("format", "jpg");

			image.put("data", imgBytes);
			image.put("name", "receipt");
			JSONArray images = new JSONArray();
			images.put(image);
			json.put("images", images);
			String postParams = json.toString();

			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(postParams);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();

			JSONObject receiptJson = new JSONObject(response.toString());
			JSONArray receiptImages = receiptJson.getJSONArray("images");
			System.out.println(receiptImages);

			List<ReceiptProduct> receiptProductList = new ArrayList<>();

			for (int i = 0; i < receiptImages.length(); i++) {
				JSONObject receiptImage = receiptImages.getJSONObject(i);
				log.error("receiptImage", receiptImage);
				System.out.println("receiptImage" + receiptImage);
				JSONArray subResults = receiptImage
					.getJSONObject("receipt")
					.getJSONObject("result")
					.getJSONArray("subResults");
				for (int j = 0; j < subResults.length(); j++) {
					JSONObject subResult = subResults.getJSONObject(j);
					JSONArray items = subResult.getJSONArray("items");

					String name = "";
					String price = "";

					for (int k = 0; k < items.length(); k++) {
						JSONObject item = items.getJSONObject(k);
						ReceiptProduct receiptProduct = new ReceiptProduct();

						if (item.has("name")) {
							name = item.getJSONObject("name")
								.getJSONObject("formatted")
								.getString("value");
							System.out.println(name);

							if (item.has("price") && item.getJSONObject("price").has("price")) {
								price = item.getJSONObject("price")
									.getJSONObject("price")
									.getJSONObject("formatted")
									.getString("value");
								System.out.println(price);
								receiptProduct.setName(name);
								receiptProduct.setCost(price);
								receiptProductList.add(receiptProduct);
								name = "";
								price = "";
							}
						} else {
							price = item.getJSONObject("price")
								.getJSONObject("price")
								.getJSONObject("formatted")
								.getString("value");
							System.out.println(price);
							receiptProduct.setName(name);
							receiptProduct.setCost(price);
							receiptProductList.add(receiptProduct);
							name = "";
							price = "";

						}
					}
				}

				receiptProducts.setReceiptProducts(receiptProductList);
				return receiptProducts;
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		return receiptProducts;
	}


	@Override
	public void registerReceipt(List<ReceiptEachRequest> receiptEachRequests, User user) {

		Refrigerator refrigerator = refrigeratorRepository.findByUserId(user.getUserId())
			.orElseThrow();

		for (ReceiptEachRequest receiptEachRequest : receiptEachRequests) {
			String name = receiptEachRequest.getName();
			LocalDate expectedExpirationDate = receiptEachRequest.getExpiredDate();
			Integer price = receiptEachRequest.getPrice();
			CategoryDetail categoryDetail = categoryDetailRepository.findById(receiptEachRequest.getCategoryId())
				.orElseThrow();
			StorageTypeId location = receiptEachRequest.getLocation();

			Food food = Food.create(name, categoryDetail, price, expectedExpirationDate,
				location, refrigerator, LocalDateTime.now(), true);

			foodRepository.save(food);
		}

	}


	@Transactional(readOnly = true)
	@Override
	public List<CategoryInfoResponse> getCategoryInfo() {
		List<CategoryInfoResponse> categoryInfoResponseList = new ArrayList<>();

		for (CategoryBig categoryBig : categoryBigRepository.findAll()) {
			List<CategoryInfoDetail> categoryInfoDetails = new ArrayList<>();

			for (CategoryDetail categoryDetail : categoryDetailRepository.findAllByCategoryBig(categoryBig.getCategoryBigId())) {
				CategoryInfoDetail categoryInfoDetail = CategoryInfoDetail.create(
					categoryDetail.getCategoryDetailId(),
					categoryDetail.getName(),
					categoryDetail.getImgSrc()
				);
				categoryInfoDetails.add(categoryInfoDetail);
			}

			CategoryInfoResponse categoryInfoResponse = CategoryInfoResponse.create(
				categoryBig.getCategoryBigId(),
				categoryBig.getName(),
				categoryBig.getImgSrc(),
				categoryInfoDetails
				);
			categoryInfoResponseList.add(categoryInfoResponse);
		}

		return categoryInfoResponseList;
	}


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

	@Transactional
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
			storageTypeId, refrigerator, timeService.localDateTimeNow(), request.getIsManual()));
	}

	@Override
	public FoodStorageResponse getFoodStorage(String storage, User user) {
		Refrigerator refrigerator = refrigeratorRepository.findByUserId(user.getUserId())
			.orElseThrow(() -> new RefrigeratorException(RefrigeratorErrorCode.REFRIGERATOR_NOT_FOUND));

		List<Food> foods = foodRepository.findAllByRefrigeratorIdAndFoodStorage(
			refrigerator.getRefrigeratorId(),
			StorageTypeId.valueOf(storage.toUpperCase())
		);

		List<FoodStorage> fresh = new ArrayList<>();
		List<FoodStorage> warning = new ArrayList<>();
		List<FoodStorage> danger = new ArrayList<>();
		List<FoodStorage> rotten = new ArrayList<>();

		for (Food food : foods) {
			int dDay = Food.getDDay(food.getExpectedExpirationDate(), LocalDate.now());
			FoodStorage foodStorage = FoodStorage.create(
				food.getFoodId(),
				food.getName(),
				food.getCategoryDetail().getImgSrc(),
				dDay);

			switch (food.getFoodStatus()) {
				case FRESH -> fresh.add(foodStorage);
				case WARNING -> warning.add(foodStorage);
				case DANGER -> danger.add(foodStorage);
				case ROTTEN -> rotten.add(foodStorage);
			}
		}

		return FoodStorageResponse.create(fresh, warning, danger, rotten);
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
