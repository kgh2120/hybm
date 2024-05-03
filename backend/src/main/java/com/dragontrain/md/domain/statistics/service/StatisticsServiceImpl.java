package com.dragontrain.md.domain.statistics.service;

import com.dragontrain.md.domain.food.domain.CategoryDetail;
import com.dragontrain.md.domain.food.domain.Food;
import com.dragontrain.md.domain.food.domain.FoodDeleteType;
import com.dragontrain.md.domain.food.service.port.CategoryBigRepository;
import com.dragontrain.md.domain.food.service.port.FoodRepository;
import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorErrorCode;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorException;
import com.dragontrain.md.domain.refrigerator.service.port.RefrigeratorRepository;
import com.dragontrain.md.domain.statistics.controller.response.SpendByBigCategory;
import com.dragontrain.md.domain.statistics.controller.response.StatisticsResponse;
import com.dragontrain.md.domain.statistics.exception.StatisticsErrorCode;
import com.dragontrain.md.domain.statistics.exception.StatisticsException;
import com.dragontrain.md.domain.statistics.service.dto.BigCategoryPriceInfo;
import com.dragontrain.md.domain.statistics.service.dto.TopEatenWithCount;
import com.dragontrain.md.domain.statistics.service.dto.TopThrownWithCount;
import com.dragontrain.md.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StatisticsServiceImpl implements StatisticsService {

	private final CategoryBigRepository categoryBigRepository;
	private final RefrigeratorRepository refrigeratorRepository;
	private final FoodRepository foodRepository;
	@Override
	public StatisticsResponse findStatisticsByYearAndMonth(User user, Integer year, Integer month) {

		if(!checkYearAndMonth(user, year, month)){
			throw new StatisticsException(StatisticsErrorCode.NOT_VALIDATED_YEAR_OR_MONTH);
		}

		Refrigerator refrigerator = refrigeratorRepository.findByUserId(user.getUserId())
			.orElseThrow(() -> new RefrigeratorException(RefrigeratorErrorCode.REFRIGERATOR_NOT_FOUND));

		Map<String, List<BigCategoryPriceInfo>> infos = categoryBigRepository.findAllBigGroupAndSpend(
			refrigerator.getRefrigeratorId(), year, month
		).stream()
			.collect(Collectors.groupingBy(
				item -> item.getBigCategory(),
				Collectors.toList()
			));

		int totalPrice = 0;
		List<SpendByBigCategory> priceResult = new ArrayList<>();
		for(String bigCategory : infos.keySet()){
			int sum = 0;
			for(BigCategoryPriceInfo info : infos.get(bigCategory)){
				sum += info.getMoney();
			}
			totalPrice += sum;
			priceResult.add(
				SpendByBigCategory.create(bigCategory, sum)
			);
		}
		Collections.sort(priceResult, (o1, o2) -> Integer.compare(o2.getMoney(), o1.getMoney()));

		List<SpendByBigCategory> priceResponse;
		if(priceResult.size() > 5){
			priceResponse = new ArrayList<>();
			int tmpCount = 0;
			for(int i = 0; i < 5; i++){
				priceResponse.add(priceResult.get(i));
				tmpCount += priceResult.get(i).getMoney();
			}
			priceResponse.add(
				SpendByBigCategory.create("etc", totalPrice - tmpCount)
			);
		}
		else {
			priceResponse = priceResult;
		}

		List<Food> foods = foodRepository.findAllDeletedFoodByRefrigeratorIdAndTime(refrigerator.getRefrigeratorId(), year, month);

		Map<FoodDeleteType, Long> countEatenAndRoten = foods.stream()
			.collect(Collectors.groupingBy(
				item -> item.getFoodDeleteType(),
				Collectors.counting()
			));

		Map<CategoryDetail, List<Food>> eatenFoods = foods.stream()
			.filter(item -> item.getFoodDeleteType().equals(FoodDeleteType.EATEN))
			.collect(Collectors.groupingBy(
				item -> item.getCategoryDetail(),
				Collectors.toList()
			));

		List<TopEatenWithCount> eatenRank = new ArrayList<>();
		for(CategoryDetail categoryDetail : eatenFoods.keySet()){
			eatenRank.add(
				TopEatenWithCount.create(categoryDetail, eatenFoods.get(categoryDetail).size())
			);
		}
		Collections.sort(eatenRank, (o1, o2) -> Integer.compare(o2.getCount(), o1.getCount()));

		Map<CategoryDetail, List<Food>> thrownFoods = foods.stream()
			.filter(item -> item.getFoodDeleteType().equals(FoodDeleteType.THROWN))
			.collect(Collectors.groupingBy(
				item -> item.getCategoryDetail(),
				Collectors.toList()
			));

		List<TopThrownWithCount> rotenRank = new ArrayList<>();
		for(CategoryDetail categoryDetail : thrownFoods.keySet()){
			rotenRank.add(
				TopThrownWithCount.create(categoryDetail, thrownFoods.get(categoryDetail).size())
			);
		}
		Collections.sort(rotenRank, (o1, o2) -> Integer.compare(o2.getCount(), o1.getCount()));

		return StatisticsResponse.create(
			totalPrice,
			priceResponse,
			countEatenAndRoten,
			eatenRank,
			rotenRank
		);
	}

	private boolean checkYearAndMonth(User user, Integer year, Integer month){
		if((year >= user.getCreatedAt().getYear() && year <= LocalDateTime.now().getYear())
			|| (month >= 1 && month <= LocalDateTime.now().getMonth().getValue())){
			return true;
		}
		return false;
	}
}
