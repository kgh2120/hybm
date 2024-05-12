package com.example.recipedata.common;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReadExcel {

    public static String path = "C:\\Users\\SSAFY\\Desktop";
    public static String filename = "recipesUTF.xlsx";

    @Bean
    public void read() {
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
                System.out.print(recipeNoValue);
                System.out.println();

                String recipeNameValue;
                if (recipeName.getCellType() == CellType.NUMERIC) {
                    // NUMERIC 타입의 값을 문자열로 변환
                    recipeNameValue = String.valueOf(recipeName.getNumericCellValue());
                } else {
                    // NUMERIC 타입이 아닌 경우에 대한 처리
                    recipeNameValue = recipeName.toString(); // 다른 타입의 셀도 문자열로 변환
                }
                System.out.print(recipeNameValue);
                System.out.println();

                String recipeAuthorValue;
                if (recipeName.getCellType() == CellType.NUMERIC) {
                    // NUMERIC 타입의 값을 문자열로 변환
                    recipeAuthorValue = String.valueOf(recipeAuthor.getNumericCellValue());
                } else {
                    // NUMERIC 타입이 아닌 경우에 대한 처리
                    recipeAuthorValue = recipeAuthor.toString(); // 다른 타입의 셀도 문자열로 변환
                }
                System.out.print(recipeAuthorValue);
                System.out.println();

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
                for (String s : foodArray) {
                    if (isName) {
                        s = s.replaceAll("\\d.*", "").replace("|", "");
                        foodnames.add(s);
                        isName = false;
                    }
                    if (s.contains("]")) {
                        isName = true;
                    }
                    if (s.contains("|")) {
                        isName = true;
                    }
                }
                foodnames.forEach(System.out::println);

                List<String> foodList = foodnames.stream().toList();

                System.out.println();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
