package com.dragontrain.md.domain.statistics.service;

import com.dragontrain.md.domain.statistics.controller.response.StatisticsResponse;
import com.dragontrain.md.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StatisticsServiceImpl implements StatisticsService {
	@Override
	public StatisticsResponse findStatisticsByYearAndMonth(User user, Integer year, Integer month) {
		// 각 대분류 가격의 총합을 구하고
		// orderby desc로 내림차순 정렬

		// 자신의 음식들 싹 검색해서 상태 먹은것과 썩은것 개수 구하기

		// 자신의 음식들 먹은 음식들 싹 구하고 groupBy로 소분류 그룹핑, TOP5 orderby로 내림차순
		// 자신의 음식들 썩은 음식들 싹 구하고 groupBy로 소분류 그룹핑, TOP5 orderby로 내림차순


		return null;
	}
}
