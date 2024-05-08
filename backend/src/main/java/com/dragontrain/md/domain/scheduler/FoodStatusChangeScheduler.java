package com.dragontrain.md.domain.scheduler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dragontrain.md.common.service.TimeService;
import com.dragontrain.md.domain.food.domain.Food;
import com.dragontrain.md.domain.food.domain.FoodStatus;
import com.dragontrain.md.domain.food.service.port.FoodRepository;
import com.dragontrain.md.domain.notice.service.NoticeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class FoodStatusChangeScheduler {

	private final FoodRepository foodRepository;
	private final TimeService timeService;
	private final NoticeService noticeService;

	@Scheduled(cron = "0 0 4 * * *", zone = "Asia/Seoul")
	public void changeFoodStatus() {
		LocalDate now = timeService.localDateNow();
		log.debug("{}월 {}일자 - 음식 스케줄러 돌아갑니다", now.getMonthValue(), now.getDayOfMonth());

		LocalDateTime localDateTime = timeService.localDateTimeNow();

		List<Food> toDanger = foodRepository.findFoodByDDay(3, now);
		toDanger.forEach(food -> food.changeStatus(FoodStatus.DANGER, localDateTime));


		List<Food> toRotten = foodRepository.findFoodByDDay(0, now);
		toRotten.forEach(food -> food.changeStatus(FoodStatus.ROTTEN, localDateTime));
		toDanger.addAll(toRotten);
		noticeService.saveNotices(toDanger);
	}

}
