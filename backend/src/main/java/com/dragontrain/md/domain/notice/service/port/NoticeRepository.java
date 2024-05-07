package com.dragontrain.md.domain.notice.service.port;

import com.dragontrain.md.domain.notice.domain.Notice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.Optional;

public interface NoticeRepository {
	Slice<Notice> findAllNotDeletedNotice(Long refrigeratorId, Pageable pageable);

	Boolean existsNewNotice(Long refrigeratorId);

	Optional<Notice> findByNoticeId(Long noticeId);
}
