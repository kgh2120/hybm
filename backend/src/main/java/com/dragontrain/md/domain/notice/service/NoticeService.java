package com.dragontrain.md.domain.notice.service;

import com.dragontrain.md.domain.notice.controller.response.AllNoticeResponse;
import com.dragontrain.md.domain.user.domain.User;
import org.springframework.data.domain.Pageable;

public interface NoticeService {
	AllNoticeResponse findAllNotDeletedNotice(User user, Pageable pageable);
}
