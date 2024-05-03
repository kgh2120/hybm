package com.dragontrain.md.domain.statistics.controller.response;

import com.dragontrain.md.domain.food.domain.FoodDeleteType;
import com.dragontrain.md.domain.statistics.service.dto.TopEatenWithCount;
import com.dragontrain.md.domain.statistics.service.dto.TopThrownWithCount;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class StatisticsResponse {
	private Integer total;
	private List<SpendByBigCategory> spend;
	private Long eaten;
	private Long thrown;
	private List<TopEaten> topEaten;
	private List<TopThrown> topThrown;

	public static StatisticsResponse create(Integer totalPrice,
											List<SpendByBigCategory>spendByBigCategories,
											Map<FoodDeleteType, Long> countEatenAndRoten,
											List<TopEatenWithCount> eatenRank,
											List<TopThrownWithCount> thrownRank)
	{
		return StatisticsResponse.builder()
			.total(totalPrice)
			.spend(spendByBigCategories)
			.eaten(countEatenAndRoten.get(FoodDeleteType.EATEN) != null ? countEatenAndRoten.get(FoodDeleteType.EATEN) : 0)
			.thrown(countEatenAndRoten.get(FoodDeleteType.THROWN) != null ? countEatenAndRoten.get(FoodDeleteType.THROWN) : 0)
			.topEaten(eatenRank.stream().map(TopEaten::createByTopEatenWithCount).limit(5).toList())
			.topThrown(thrownRank.stream().map(TopThrown::createByTopThrownWithCount).limit(5).toList())
			.build();
	}
}
