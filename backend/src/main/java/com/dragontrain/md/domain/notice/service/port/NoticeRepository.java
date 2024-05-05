package com.dragontrain.md.domain.notice.service.port;

import com.dragontrain.md.domain.notice.domain.Notice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface NoticeRepository {
	Slice<Notice> findAllNotDeletedNotice(Pageable pageable);
}
