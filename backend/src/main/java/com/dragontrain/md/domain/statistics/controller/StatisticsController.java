package com.dragontrain.md.domain.statistics.controller;

import com.dragontrain.md.domain.statistics.controller.response.StatisticsResponse;
import com.dragontrain.md.domain.statistics.service.StatisticsService;
import com.dragontrain.md.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

	private final StatisticsService statisticsService;

	@GetMapping("/month")
	public ResponseEntity<StatisticsResponse> findStatisticsByYearAndMonth(
		@AuthenticationPrincipal User user,
		@RequestParam Integer year,
		@RequestParam Integer month
	){
		return ResponseEntity.ok(statisticsService.findStatisticsByYearAndMonth(user, year, month));
	}
}
