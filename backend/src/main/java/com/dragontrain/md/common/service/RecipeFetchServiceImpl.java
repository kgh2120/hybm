package com.dragontrain.md.common.service;

import com.dragontrain.md.domain.food.domain.CategoryDetail;
import com.dragontrain.md.domain.food.service.port.CategoryDetailRepository;
import com.dragontrain.md.domain.recipe.domain.Ingredient;
import com.dragontrain.md.domain.recipe.service.port.IngredientRepository;
import com.dragontrain.md.domain.recipe.service.port.RecipeIngredientRepository;
import com.dragontrain.md.domain.recipe.service.port.RecipeRepository;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorErrorCode;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorException;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RecipeFetchServiceImpl implements RecipeFetchService {

	public static String path = "C:\\Users\\kyoohyun\\Desktop";
	public static String filename = "recipe.xlsx";

	private final RecipeRepository recipeRepository;
	private final IngredientRepository ingredientRepository;
	private final RecipeIngredientRepository recipeIngredientRepository;
	private final CategoryDetailRepository categoryDetailRepository;

	public void fetchRecipeData(int start, int end) {
		System.out.println("가자");
		try (FileInputStream file = new FileInputStream(new File(path, filename))) {

			XSSFWorkbook workbook = new XSSFWorkbook(file);

			XSSFSheet sheet = workbook.getSheetAt(0);

			for (int i = start; i < end; i++) {
				XSSFRow row = sheet.getRow(i);
				Cell recipeNo = row.getCell(0);
				if (recipeNo == null) {
					continue;
				}

				Cell recipeName = row.getCell(2);
				if (recipeName == null) {
					continue;
				}

				Cell recipeAuthor = row.getCell(4);
				if (recipeAuthor == null) {
					continue;
				}

				Cell recipeIngredients = row.getCell(13);
				if (recipeIngredients == null) {
					continue;
				}

				int recipeNoValue;
				if (recipeNo.getCellType() != CellType.NUMERIC) {
					// NUMERIC 타입의 값을 문자열로 변환
					continue;
				} else {
					// NUMERIC 타입이 아닌 경우에 대한 처리
					recipeNoValue = (int) recipeNo.getNumericCellValue(); // 다른 타입의 셀도 문자열로 변환
				}

				String recipeNameValue;
				if (recipeName.getCellType() == CellType.NUMERIC) {
					// NUMERIC 타입의 값을 문자열로 변환
					recipeNameValue = String.valueOf(recipeName.getNumericCellValue());
				} else {
					// NUMERIC 타입이 아닌 경우에 대한 처리
					recipeNameValue = recipeName.toString(); // 다른 타입의 셀도 문자열로 변환
				}

				String recipeAuthorValue;
				if (recipeName.getCellType() == CellType.NUMERIC) {
					// NUMERIC 타입의 값을 문자열로 변환
					recipeAuthorValue = String.valueOf(recipeAuthor.getNumericCellValue());
				} else {
					// NUMERIC 타입이 아닌 경우에 대한 처리
					recipeAuthorValue = recipeAuthor.toString(); // 다른 타입의 셀도 문자열로 변환
				}
				recipeRepository.save(recipeNoValue, recipeNameValue, recipeAuthorValue);


				String foods;
				if (recipeIngredients.getCellType() == CellType.NUMERIC) {
					// NUMERIC 타입의 값을 문자열로 변환
					foods = String.valueOf(recipeIngredients.getNumericCellValue());
				} else {
					// NUMERIC 타입이 아닌 경우에 대한 처리
					foods = recipeIngredients.toString(); // 다른 타입의 셀도 문자열로 변환
				}
				String[] foodArray = foods.split(" ");

				boolean isName = false;
				for (String s : foodArray) {
					if (isName) {

						// 레시피 재료 이름 정제
						s = s.replaceAll("\\d.*", "").replace("|", "");
						String ingredientName = s;

						// 레시피 재료 이름이 재료 테이블에 있으면 중개 테이블에 저장하고 건너뜀
						if (ingredientRepository.existsByName(ingredientName)) {

							// 레시피-재료 중개 테이블에 저장
							ingredientRepository.findAllIngredientIdsByName(ingredientName).forEach(
								ingredientId -> {
									if (!recipeIngredientRepository.existsById(recipeNoValue, ingredientId)) {
										recipeIngredientRepository.save(recipeNoValue, ingredientId);
									}
								}
							);
							isName = false;
							continue;
						}

						// 소분류 아이디를 이름으로 검색, 없으면 null 처리
						if (categoryDetailRepository.existsByName(ingredientName)) {

							List<CategoryDetail> cd = categoryDetailRepository.findByName(ingredientName);

							cd.forEach(e -> {

								Integer categoryDetailId = e.getCategoryDetailId();
								ingredientRepository.save(ingredientName, categoryDetailId);

								Ingredient ingredient = ingredientRepository.findByIngredientNameAndCategoryDetailId(
									ingredientName, categoryDetailId
									).orElseThrow(() -> new RefrigeratorException(RefrigeratorErrorCode.INVALID_BADGE_REQUEST));

								recipeIngredientRepository.save(recipeNoValue, ingredient.getIngredientId());
							});
							isName = false;
							continue;
						} else {
							ingredientRepository.save(ingredientName, null);

							Ingredient ingredient = ingredientRepository.findByIngredientNameAndCategoryDetailId(
								ingredientName, null
								).orElseThrow(() -> new RefrigeratorException(RefrigeratorErrorCode.REFRIGERATOR_NOT_FOUND));

							recipeIngredientRepository.save(recipeNoValue, ingredient.getIngredientId());
						}

						isName = false;
					}
					if (s.contains("]")) {
						isName = true;
					}
					if (s.contains("|")) {
						isName = true;
					}
				}
			}
			System.out.println("끗");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
