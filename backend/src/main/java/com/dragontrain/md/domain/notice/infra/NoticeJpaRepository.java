package com.dragontrain.md.domain.notice.infra;

import com.dragontrain.md.domain.notice.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeJpaRepository extends JpaRepository<Notice, Long> {

}
