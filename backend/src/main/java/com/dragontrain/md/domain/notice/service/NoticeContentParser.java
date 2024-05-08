package com.dragontrain.md.domain.notice.service;

import org.springframework.stereotype.Component;

import com.dragontrain.md.domain.food.domain.Food;
import com.dragontrain.md.domain.food.domain.FoodStatus;

@Component
public class NoticeContentParser {
	public String parseNoticeContent(Food food){
		return new StringBuilder().append("'")
			.append(food.getStorageType().getTypeName())
			.append("'에서 '")
			.append(food.getName())
			.append("'이/가 ")
			.append(food.getFoodStatus().equals(FoodStatus.DANGER) ? "3일 남았습니다." : "썩어가요.")
			.toString();
	}
}
