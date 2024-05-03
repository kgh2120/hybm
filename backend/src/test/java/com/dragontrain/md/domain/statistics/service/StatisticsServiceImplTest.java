package com.dragontrain.md.domain.statistics.service;

import com.dragontrain.md.domain.TestEntityFactory;
import com.dragontrain.md.domain.food.domain.CategoryBig;
import com.dragontrain.md.domain.food.domain.CategoryDetail;
import com.dragontrain.md.domain.food.domain.Food;
import com.dragontrain.md.domain.food.domain.FoodDeleteType;
import com.dragontrain.md.domain.food.service.port.CategoryBigRepository;
import com.dragontrain.md.domain.food.service.port.FoodRepository;
import com.dragontrain.md.domain.refrigerator.domain.Level;
import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;
import com.dragontrain.md.domain.refrigerator.domain.StorageType;
import com.dragontrain.md.domain.refrigerator.service.port.RefrigeratorRepository;
import com.dragontrain.md.domain.statistics.controller.response.StatisticsResponse;
import com.dragontrain.md.domain.statistics.service.dto.BigCategoryPriceInfo;
import com.dragontrain.md.domain.user.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class StatisticsServiceImplTest {

	@Mock
	private CategoryBigRepository categoryBigRepository;
	@Mock
	private RefrigeratorRepository refrigeratorRepository;
	@Mock
	private FoodRepository foodRepository;

	@InjectMocks
	private StatisticsServiceImpl statisticsService;

	private static TestEntityFactory testEntityFactory;

	@BeforeAll
	static void 장전(){
		testEntityFactory = new TestEntityFactory();
	}

	@Test
	void 월간통계획득_성공(){
		User user = testEntityFactory.getTestUserEntity(1L);
		Level level = testEntityFactory.getTestLevelEntity(1, 1, 1);
		Refrigerator refrigerator = testEntityFactory.getTestRefrigerator(1L, user, Boolean.FALSE, level);

		List<CategoryBig> categoryBigs = new ArrayList<>();
		for(int i = 1; i <= 7; i++){
			categoryBigs.add(
				testEntityFactory.getCategoryBig(Integer.toString(i * 1000), i + "번이미지")
			);
		}

		List<Food> foodList = new ArrayList<>();
		for(CategoryBig categoryBig : categoryBigs){
			List<CategoryDetail> categoryDetails = new ArrayList<>();
			for(int i = 1; i <= 6; i++){
				CategoryDetail tcd = testEntityFactory.getCategoryDetail((Integer.parseInt(categoryBig.getName()) + i) + "소분류", i + "번이미지", categoryBig);
				for(int j = 0; j < 10; j++){
					Food tfe = testEntityFactory.getDeletedFood("f1", 5000, refrigerator, StorageType.builder().build(), tcd, LocalDateTime.now(), LocalDateTime.now(), FoodDeleteType.EATEN, LocalDateTime.now());
					Food tft = testEntityFactory.getDeletedFood("f2", 5000, refrigerator, StorageType.builder().build(), tcd, LocalDateTime.now(), LocalDateTime.now(), FoodDeleteType.THROWN, LocalDateTime.now());
					foodList.add(tfe);
					foodList.add(tft);
				}
				categoryDetails.add(tcd);
			}
		}

		List<BigCategoryPriceInfo> infos = new ArrayList<>();
		for(Food food : foodList){
			infos.add(
				BigCategoryPriceInfo.builder()
					.bigCategory(food.getCategoryDetail().getCategoryBig().getName())
					.money(food.getPrice())
					.build()
			);
		}

		BDDMockito.given(refrigeratorRepository.findByUserId(any()))
			.willReturn(Optional.of(refrigerator));

		BDDMockito.given(categoryBigRepository.findAllBigGroupAndSpend(any(), any(), any()))
				.willReturn(infos);

		BDDMockito.given(foodRepository.findAllDeletedFoodByRefrigeratorIdAndTime(any(), any(), any()))
			.willReturn(foodList);

		Assertions.assertDoesNotThrow(() -> statisticsService.findStatisticsByYearAndMonth(user, 1, 1));

		StatisticsResponse statisticsResponse = statisticsService.findStatisticsByYearAndMonth(user, 1, 1);

		Assertions.assertEquals(statisticsResponse.getTopEatenDetailCategory().size(), 5);
		Assertions.assertEquals(statisticsResponse.getTopThrownDetailCategory().size(), 5);
		Assertions.assertEquals(statisticsResponse.getSpendByBigCategory().size(), 6);
		Assertions.assertEquals(statisticsResponse.getEatenCount(), 420);
		Assertions.assertEquals(statisticsResponse.getThrownCount(), 420);
		Assertions.assertEquals(statisticsResponse.getTotalSpend(), 4200000);
	}
}
