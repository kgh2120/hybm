package com.dragontrain.md.domain.statistics.service;

import com.dragontrain.md.domain.statistics.controller.response.StatisticsResponse;
import com.dragontrain.md.domain.user.domain.User;

public interface StatisticsService {
	StatisticsResponse findStatisticsByYearAndMonth(User user, Integer year, Integer month);
}
