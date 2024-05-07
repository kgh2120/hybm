package com.dragontrain.md.domain.notice.service;

import com.dragontrain.md.domain.notice.controller.response.AllNoticeResponse;
import com.dragontrain.md.domain.notice.service.port.NoticeRepository;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorErrorCode;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorException;
import com.dragontrain.md.domain.refrigerator.service.port.RefrigeratorRepository;
import com.dragontrain.md.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService{
	private final NoticeRepository noticeRepository;
	private final RefrigeratorRepository refrigeratorRepository;
	@Override
	public AllNoticeResponse findAllNotDeletedNotice(User user, Pageable pageable) {
		return AllNoticeResponse.create(
			noticeRepository.findAllNotDeletedNotice(
				refrigeratorRepository.findByUserId(user.getUserId())
			.orElseThrow(() -> new RefrigeratorException(RefrigeratorErrorCode.REFRIGERATOR_NOT_FOUND))
			.getRefrigeratorId()
				, pageable
			)
		);
	}
}
