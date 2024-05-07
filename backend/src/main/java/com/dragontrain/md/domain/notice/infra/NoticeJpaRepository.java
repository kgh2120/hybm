package com.dragontrain.md.domain.notice.infra;

import com.dragontrain.md.domain.notice.domain.Notice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NoticeJpaRepository extends JpaRepository<Notice, Long> {
	@Query("select n from Notice n join n.food f join f.refrigerator r" +
		   " on r.refrigeratorId=:refrigeratorId and n.deletedAt is null")
	Slice<Notice> findAllNotDeletedNoticeByPage(Long refrigeratorId, Pageable pageable);

	Boolean existsByFood_Refrigerator_RefrigeratorIdAndDeletedAtIsNullAndIsCheckedIsFalse(Long refrigeratorId);

	Optional<Notice> findByNoticeId(Long noticeId);

	List<Notice> findAllByFood_Refrigerator_RefrigeratorIdAndDeletedAtIsNull(Long refrigeratorId);
}
