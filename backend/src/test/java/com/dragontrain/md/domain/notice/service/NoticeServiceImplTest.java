package com.dragontrain.md.domain.notice.service;

import com.dragontrain.md.common.config.properties.ExpProperties;
import com.dragontrain.md.common.service.EventPublisher;
import com.dragontrain.md.common.service.TimeService;
import com.dragontrain.md.domain.food.domain.CategoryDetail;
import com.dragontrain.md.domain.food.domain.Food;
import com.dragontrain.md.domain.notice.controller.response.AllNoticeResponse;
import com.dragontrain.md.domain.notice.domain.Notice;
import com.dragontrain.md.domain.notice.domain.NoticeType;
import com.dragontrain.md.domain.notice.exception.NoticeException;
import com.dragontrain.md.domain.notice.service.port.NoticeRepository;
import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;
import com.dragontrain.md.domain.refrigerator.service.port.RefrigeratorRepository;
import com.dragontrain.md.domain.user.domain.User;
import static org.mockito.ArgumentMatchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
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

import com.dragontrain.md.setting.TestEntityFactory;

@ExtendWith(MockitoExtension.class)
class NoticeServiceImplTest {

	@Mock
	private NoticeRepository noticeRepository;

	@Mock
	private RefrigeratorRepository refrigeratorRepository;

	@Mock
	private TimeService timeService;

	@Mock EventPublisher eventPublisher;
	@Mock ExpProperties expProperties;

	@InjectMocks
	private NoticeServiceImpl noticeService;

	private TestEntityFactory testEntityFactory = new TestEntityFactory();

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
		Slice<Notice> noticeSlice = new SliceImpl<>(notices, pageable, false);

		BDDMockito.given(refrigeratorRepository.findByUserId(any()))
			.willReturn(Optional.of(testEntityFactory.getTestRefrigerator(1L, null, Boolean.FALSE, null)));

		BDDMockito.given(noticeRepository.findAllNotDeletedNoticeByPage(any(), any()))
			.willReturn(noticeSlice);

		AllNoticeResponse allNoticeResponse = noticeService.findAllNotDeletedNotice(user, pageable);
		Assertions.assertEquals(allNoticeResponse.getNotice().size(), 10);
		Assertions.assertEquals(allNoticeResponse.getHasNext(), false);
	}

	@WithMockUser
	@Test
	void 내알림삭제_성공(){
		User user = testEntityFactory.getTestUserEntity(1L);
		Refrigerator refrigerator = testEntityFactory.getTestRefrigerator(1L, user, Boolean.FALSE, null);
		Food food = testEntityFactory.getFood("음식", 9999, refrigerator, null, null, timeService.localDateTimeNow(), timeService.localDateTimeNow());
		Notice notice = testEntityFactory.getNotice("알림", Boolean.TRUE, NoticeType.TO_DANGER, food);

		BDDMockito.given(noticeRepository.findByNoticeId(any()))
			.willReturn(Optional.of(notice));

		BDDMockito.given(refrigeratorRepository.findByUserId(any()))
			.willReturn(Optional.of(refrigerator));

		Assertions.assertDoesNotThrow(() -> noticeService.deleteNotice(user, 1L));
	}

	@WithMockUser
	@Test
	void 남의알림삭제_실패(){
		User user = testEntityFactory.getTestUserEntity(1L);
		User otherUser = testEntityFactory.getTestUserEntity(2L);
		Refrigerator refrigerator = testEntityFactory.getTestRefrigerator(1L, user, Boolean.FALSE, null);
		Refrigerator otherRefrigerator = testEntityFactory.getTestRefrigerator(2L, otherUser, Boolean.FALSE, null);
		Food otherFood = testEntityFactory.getFood("남의음식", 9999, otherRefrigerator, null, null, timeService.localDateTimeNow(), timeService.localDateTimeNow());
		Notice otherNotice = testEntityFactory.getNotice("알림", Boolean.TRUE, NoticeType.TO_DANGER, otherFood);

		BDDMockito.given(noticeRepository.findByNoticeId(any()))
			.willReturn(Optional.of(otherNotice));

		BDDMockito.given(refrigeratorRepository.findByUserId(any()))
			.willReturn(Optional.of(refrigerator));

		Assertions.assertThrows(NoticeException.class, () -> noticeService.deleteNotice(user, 1L));
	}

	@WithMockUser
	@Test
	void 삭제된알림삭제_실패(){
		User user = testEntityFactory.getTestUserEntity(1L);
		Refrigerator refrigerator = testEntityFactory.getTestRefrigerator(1L, user, Boolean.FALSE, null);
		Food food = testEntityFactory.getFood("음식", 9999, refrigerator, null, null, timeService.localDateTimeNow(), timeService.localDateTimeNow());
		Notice notice = testEntityFactory.getDeletedNotice("알림", Boolean.TRUE, NoticeType.TO_DANGER, food);

		BDDMockito.given(noticeRepository.findByNoticeId(any()))
			.willReturn(Optional.of(notice));

		BDDMockito.given(refrigeratorRepository.findByUserId(any()))
			.willReturn(Optional.of(refrigerator));

		Assertions.assertThrows(NoticeException.class, () -> noticeService.deleteNotice(user, 1L));
	}

	@WithMockUser
	@Test
	void 내_모든_알림삭제_성공(){
		User user = testEntityFactory.getTestUserEntity(1L);
		Refrigerator refrigerator = testEntityFactory.getTestRefrigerator(1L, user, Boolean.FALSE, null);
		List<Notice> notices = new ArrayList<>();
		for(int i = 0; i < 10; i++){
			notices.add(
				testEntityFactory.getNotice("알림", Boolean.TRUE, NoticeType.TO_DANGER, null)
			);
		}

		BDDMockito.given(refrigeratorRepository.findByUserId(any()))
			.willReturn(Optional.of(refrigerator));

		BDDMockito.given(noticeRepository.findAllNotDeletedNotice(any()))
			.willReturn(notices);

		BDDMockito.given(timeService.localDateTimeNow())
				.willReturn(LocalDateTime.now());

		Assertions.assertDoesNotThrow(() -> noticeService.deleteAllNotice(user));
	}
}
