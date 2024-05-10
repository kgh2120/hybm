package com.dragontrain.md.common.service;

import com.dragontrain.md.domain.food.service.port.CategoryDetailRepository;
import com.dragontrain.md.domain.recipe.service.port.IngredientRepository;
import com.dragontrain.md.domain.recipe.service.port.RecipeIngredientRepository;
import com.dragontrain.md.domain.recipe.service.port.RecipeRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class FileServiceImpl implements FileService{

	public static String path = "C:\\Users\\SSAFY\\Desktop";
	public static String filename = "recipesUTF.xlsx";

	private final RecipeRepository recipeRepository;
	private final IngredientRepository ingredientRepository;
	private final RecipeIngredientRepository recipeIngredientRepository;
	private final CategoryDetailRepository categoryDetailRepository;

	@Override
	public void setData() {
		System.out.println("가자");
		try (FileInputStream file = new FileInputStream(new File(path, filename))) {

			XSSFWorkbook workbook = new XSSFWorkbook(file);

			XSSFSheet sheet = workbook.getSheetAt(0);

			for (Row row : sheet) {
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
//                System.out.print(recipeNoValue);
//                System.out.println();

				String recipeNameValue;
				if (recipeName.getCellType() == CellType.NUMERIC) {
					// NUMERIC 타입의 값을 문자열로 변환
					recipeNameValue = String.valueOf(recipeName.getNumericCellValue());
				} else {
					// NUMERIC 타입이 아닌 경우에 대한 처리
					recipeNameValue = recipeName.toString(); // 다른 타입의 셀도 문자열로 변환
				}
//                System.out.print(recipeNameValue);
//                System.out.println();

				String recipeAuthorValue;
				if (recipeName.getCellType() == CellType.NUMERIC) {
					// NUMERIC 타입의 값을 문자열로 변환
					recipeAuthorValue = String.valueOf(recipeAuthor.getNumericCellValue());
				} else {
					// NUMERIC 타입이 아닌 경우에 대한 처리
					recipeAuthorValue = recipeAuthor.toString(); // 다른 타입의 셀도 문자열로 변환
				}
//                System.out.print(recipeAuthorValue);
//                System.out.println();
				recipeRepository.save(recipeNameValue, recipeAuthorValue);


				String foods;
				if (recipeIngredients.getCellType() == CellType.NUMERIC) {
					// NUMERIC 타입의 값을 문자열로 변환
					foods = String.valueOf(recipeIngredients.getNumericCellValue());
				} else {
					// NUMERIC 타입이 아닌 경우에 대한 처리
					foods = recipeIngredients.toString(); // 다른 타입의 셀도 문자열로 변환
				}
				String[] foodArray = foods.split(" ");
				ArrayList<String> foodnames = new ArrayList<>() ;
				boolean isName = false;
				for (String ingredientName : foodArray) {
					if (isName) {
						ingredientName = ingredientName.replaceAll("\\d.*", "").replace("|", "");
//						if (ingredientRepository.existsByName(ingredientName)) {
//							continue;
//						}
//						Integer categoryDetailId = categoryDetailRepository.findByName(ingredientName);
						ingredientRepository.save(ingredientName, null);
						foodnames.add(ingredientName);
						isName = false;
					}
					if (ingredientName.contains("]")) {
						isName = true;
					}
					if (ingredientName.contains("|")) {
						isName = true;
					}
				}
//                foodnames.forEach(System.out::println);

				List<String> foodList = foodnames.stream().toList();


			}
			System.out.println("끗");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
