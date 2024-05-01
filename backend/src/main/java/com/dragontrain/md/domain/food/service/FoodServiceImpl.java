package com.dragontrain.md.domain.food.service;

import com.dragontrain.md.domain.food.controller.request.ReceiptRequest;
import com.dragontrain.md.domain.food.controller.response.ReceiptProduct;
import com.dragontrain.md.domain.food.controller.response.ReceiptProducts;
import com.dragontrain.md.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodServiceImpl implements FoodService {

	// OCR General 형식의 SECRET key, API URL
	public static String OCR_SECRET = "b3JxaEhVYkdmT0R3eGV5Sk50ZE9jWW9BVXNRZ1lkZmg=";
	public static String API_URL = "https://wfhz3a1k1w.apigw.ntruss.com/custom/v1/30488/74656867a5abc001d68c3c331b2cafc14096d4e749c14cedb25660009fbfe93f/general";

	// OCR DOCUMENT 형식의 SECRET key, API URL
	public static String RECEIPT_SECRET = "YWNmSXFqR3ZnakJQUUJRakp6T3JmTFhVYmZpRG1uenM=";
	public static String RECEIPT_API_URL = "https://puztzs8le0.apigw.ntruss.com/custom/v1/30517/4d2d5ffbdc42a7778d0b0c57b540a0befce43d0c5c9c03cfaca1b3462e282205/document/receipt";

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
			image.put("url", imgURL); // image should be public, otherwise, should use data
			// FileInputStream inputStream = new FileInputStream("YOUR_IMAGE_FILE");
			// byte[] buffer = new byte[inputStream.available()];
			// inputStream.read(buffer);
			// inputStream.close();
			// image.put("data", buffer);
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
	public Void registerReceipt(ReceiptRequest receiptRequest, User user) {

		return null;
	}
}

