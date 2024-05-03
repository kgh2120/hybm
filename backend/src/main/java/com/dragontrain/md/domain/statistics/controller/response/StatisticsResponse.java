package com.dragontrain.md.domain.statistics.controller.response;

import lombok.*;

import java.util.List;

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
}
