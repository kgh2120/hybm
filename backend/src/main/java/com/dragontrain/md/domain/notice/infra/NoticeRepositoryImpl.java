package com.dragontrain.md.domain.notice.infra;

import com.dragontrain.md.domain.notice.domain.Notice;
import com.dragontrain.md.domain.notice.service.port.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class NoticeRepositoryImpl implements NoticeRepository {
	private final NoticeJpaRepository noticeJpaRepository;
	@Override
	public Slice<Notice> findAllNotDeletedNotice(Long refrigeratorId, Pageable pageable) {
		return noticeJpaRepository.findAllNotDeletedNotice(refrigeratorId, pageable);
	}

	@Override
	public Boolean existsNewNotice(Long refrigeratorId) {
		return noticeJpaRepository.existsByFood_Refrigerator_RefrigeratorIdAndDeletedAtIsNullAndIsCheckedIsFalse(refrigeratorId);
	}

	@Override
	public Optional<Notice> findByNoticeId(Long noticeId) {
		return noticeJpaRepository.findByNoticeId(noticeId);
	}
}
