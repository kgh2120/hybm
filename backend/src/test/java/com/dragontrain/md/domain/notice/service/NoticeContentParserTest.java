package com.dragontrain.md.domain.notice.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dragontrain.md.domain.food.domain.Food;
import com.dragontrain.md.domain.food.domain.FoodStatus;
import com.dragontrain.md.domain.refrigerator.domain.StorageType;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;

class NoticeContentParserTest {

	NoticeContentParser noticeContentParser;

	@BeforeEach
	void beforeEach(){
		noticeContentParser = new NoticeContentParser();
	}

	@Test
	void parseNoticeContentTest() throws Exception {
		// given
		Food food = Food.builder()
			.name("쿠키")
			.foodStatus(FoodStatus.DANGER)
			.storageType(StorageType.builder()
				.storageType(StorageTypeId.COOL)
				.typeName("냉장칸")
				.build())
			.build();
		// when
		String sut = noticeContentParser.parseNoticeContent(food);
		// then
		assertThat(sut).isEqualTo("'냉장칸'에서 '쿠키'이/가 3일 남았습니다.");
	}

}
