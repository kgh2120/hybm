package com.dragontrain.md.domain.notice.service;

import com.dragontrain.md.common.service.TimeService;
import com.dragontrain.md.domain.food.domain.Food;
import com.dragontrain.md.domain.notice.controller.response.AllNoticeResponse;
import com.dragontrain.md.domain.notice.controller.response.HasnewNoticeResponse;
import com.dragontrain.md.domain.notice.domain.Notice;
import com.dragontrain.md.domain.notice.exception.NoticeErrorCode;
import com.dragontrain.md.domain.notice.exception.NoticeException;
import com.dragontrain.md.domain.notice.service.port.NoticeRepository;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorErrorCode;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorException;
import com.dragontrain.md.domain.refrigerator.service.port.RefrigeratorRepository;
import com.dragontrain.md.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService{
	private final NoticeRepository noticeRepository;
	private final RefrigeratorRepository refrigeratorRepository;
	private final TimeService timeService;
	private final NoticeContentParser noticeContentParser;
	@Override
	public AllNoticeResponse findAllNotDeletedNotice(User user, Pageable pageable) {
		return AllNoticeResponse.create(
			noticeRepository.findAllNotDeletedNoticeByPage(
				refrigeratorRepository.findByUserId(user.getUserId())
			.orElseThrow(() -> new RefrigeratorException(RefrigeratorErrorCode.REFRIGERATOR_NOT_FOUND))
			.getRefrigeratorId()
				, pageable
			)
		);
	}

	@Override
	public HasnewNoticeResponse existsNewNotice(User user) {
		return HasnewNoticeResponse.create(
			noticeRepository.existsNewNotice(
				refrigeratorRepository.findByUserId(user.getUserId())
					.orElseThrow(() -> new RefrigeratorException(RefrigeratorErrorCode.REFRIGERATOR_NOT_FOUND))
					.getRefrigeratorId()
			)
		);
	}

	@Override
	public void deleteNotice(User user, Long noticeId) {
		Notice notice = noticeRepository.findByNoticeId(noticeId)
			.orElseThrow(() -> new NoticeException(NoticeErrorCode.NOTICE_NOT_FOUND));

		if(!notice.getFood().getRefrigerator().getRefrigeratorId().equals(
			refrigeratorRepository.findByUserId(user.getUserId())
				.orElseThrow(() -> new RefrigeratorException(RefrigeratorErrorCode.REFRIGERATOR_NOT_FOUND))
				.getRefrigeratorId()
		)){
			throw new NoticeException(NoticeErrorCode.NOT_MY_NOTICE);
		}

		notice.delete(timeService.localDateTimeNow());
	}

	@Override
	public void deleteAllNotice(User user) {
		List<Notice> notices = noticeRepository.findAllNotDeletedNotice(
			refrigeratorRepository.findByUserId(user.getUserId())
				.orElseThrow(() -> new RefrigeratorException(RefrigeratorErrorCode.REFRIGERATOR_NOT_FOUND))
				.getRefrigeratorId()
		);

		notices.forEach(notice -> notice.delete(timeService.localDateTimeNow()));
	}

	@Override
	public void saveNotices(List<Food> foods) {
		LocalDateTime now = timeService.localDateTimeNow();
		foods.forEach(food ->
			noticeRepository.save(Notice.create(food, noticeContentParser.parseNoticeContent(food),now )
			//
			));

	}
}
