package com.dragontrain.md.domain.notice.service;

import com.dragontrain.md.domain.notice.controller.response.AllNoticeResponse;
import com.dragontrain.md.domain.notice.service.port.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService{
	private final NoticeRepository noticeRepository;

	@Override
	public AllNoticeResponse findAllNotDeletedNotice(Pageable pageable) {
		return null;
	}
}
