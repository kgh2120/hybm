package com.dragontrain.md.domain.notice.service;

import com.dragontrain.md.domain.TestEntityFactory;
import com.dragontrain.md.domain.food.domain.CategoryDetail;
import com.dragontrain.md.domain.food.domain.Food;
import com.dragontrain.md.domain.notice.controller.response.AllNoticeResponse;
import com.dragontrain.md.domain.notice.domain.Notice;
import com.dragontrain.md.domain.notice.domain.NoticeType;
import com.dragontrain.md.domain.notice.service.port.NoticeRepository;
import com.dragontrain.md.domain.refrigerator.service.port.RefrigeratorRepository;
import com.dragontrain.md.domain.user.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class NoticeServiceImplTest {

	@Mock
	private NoticeRepository noticeRepository;

	@Mock
	private RefrigeratorRepository refrigeratorRepository;

	@InjectMocks
	private NoticeServiceImpl noticeService;

	private static TestEntityFactory testEntityFactory;

	@BeforeAll
	static void 장전(){
		testEntityFactory = new TestEntityFactory();
	}

	@WithMockUser
	@Test
	void 알림전체조회_성공(){
		User user = testEntityFactory.getTestUserEntity(1L);
		CategoryDetail categoryDetail = testEntityFactory.getCategoryDetail("임시", "임시이미지", null);
		Food food = testEntityFactory.getFood("음식", 5000, null, null, categoryDetail, LocalDateTime.now(), LocalDateTime.now());

		List<Notice> notices = new ArrayList<>();
		for(int i = 0; i < 10; i++){
			notices.add(testEntityFactory.getNotice("알림", Boolean.FALSE, NoticeType.TO_DANGER, food));
		}

		Pageable pageable = PageRequest.of(1, 10);
		Slice<Notice> noticeSlice = new testSlice(notices, pageable, false);

		BDDMockito.given(refrigeratorRepository.findByUserId(any()))
			.willReturn(Optional.of(testEntityFactory.getTestRefrigerator(1L, null, Boolean.FALSE, null)));

		BDDMockito.given(noticeRepository.findAllNotDeletedNotice(any(), any()))
			.willReturn(noticeSlice);

		AllNoticeResponse allNoticeResponse = noticeService.findAllNotDeletedNotice(user, pageable);
		Assertions.assertEquals(allNoticeResponse.getNotice().size(), 10);
		Assertions.assertEquals(allNoticeResponse.getHasNext(), false);
	}

	private class testSlice extends SliceImpl<Notice>{
		public testSlice(List<Notice> content, Pageable pageable, boolean hasNext) {
			super(content, pageable, hasNext);
		}
	}
}
