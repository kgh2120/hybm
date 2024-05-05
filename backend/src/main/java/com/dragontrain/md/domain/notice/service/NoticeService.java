package com.dragontrain.md.domain.notice.service;

import com.dragontrain.md.domain.notice.controller.response.AllNoticeResponse;
import org.springframework.data.domain.Pageable;

public interface NoticeService {
	AllNoticeResponse findAllNotDeletedNotice(Pageable pageable);
}
