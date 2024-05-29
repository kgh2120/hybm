package com.dragontrain.md.domain.statistics.controller.response;

import java.util.List;
import java.util.Map;

import com.dragontrain.md.domain.food.domain.FoodDeleteType;
import com.dragontrain.md.domain.statistics.service.dto.TopEatenWithCount;
import com.dragontrain.md.domain.statistics.service.dto.TopThrownWithCount;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class StatisticsResponse {
	private Integer totalSpend;
	private Boolean hasEmptyPrice;
	private List<SpendByBigCategory> spendByBigCategory;
	private Integer eatenCount;
	private Integer thrownCount;
	private List<TopEaten> topEatenDetailCategory;
	private List<TopThrown> topThrownDetailCategory;

	public static StatisticsResponse create(Integer totalPrice,
		Boolean hasEmptyPrice,
		List<SpendByBigCategory> spendByBigCategories,
		Map<FoodDeleteType, Long> countEatenAndRoten,
		List<TopEatenWithCount> eatenRank,
		List<TopThrownWithCount> thrownRank) {
		int eatenCount = 0;
		int thrownCount = 0;

		if (countEatenAndRoten.containsKey(FoodDeleteType.EATEN)
			&& countEatenAndRoten.get(FoodDeleteType.EATEN) <= Integer.MAX_VALUE) {
			eatenCount = countEatenAndRoten.get(FoodDeleteType.EATEN).intValue();
		}

		if (countEatenAndRoten.containsKey(FoodDeleteType.THROWN)
			&& countEatenAndRoten.get(FoodDeleteType.THROWN) <= Integer.MAX_VALUE) {
			thrownCount = countEatenAndRoten.get(FoodDeleteType.THROWN).intValue();
		}

		return StatisticsResponse.builder()
			.totalSpend(totalPrice)
			.hasEmptyPrice(hasEmptyPrice)
			.spendByBigCategory(spendByBigCategories)
			.eatenCount(eatenCount)
			.thrownCount(thrownCount)
			.topEatenDetailCategory(eatenRank.stream().map(TopEaten::createByTopEatenWithCount).limit(5).toList())
			.topThrownDetailCategory(thrownRank.stream().map(TopThrown::createByTopThrownWithCount).limit(5).toList())
			.build();
	}
}
