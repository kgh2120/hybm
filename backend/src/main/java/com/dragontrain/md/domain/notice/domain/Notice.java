package com.dragontrain.md.domain.notice.domain;

import com.dragontrain.md.domain.food.domain.Food;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_notice")
public class Notice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notice_id", columnDefinition = "bigint", nullable = false)
	private Long noticeId;

	@Column(name = "content", columnDefinition = "varchar(100)", nullable = false)
	private String content;

	@Column(name = "is_checked", columnDefinition = "boolean default false", nullable = false)
	private Boolean isChecked;

	@Column(name = "created_at", columnDefinition = "datetime", nullable = false)
	private LocalDateTime createdAt;
	@Column(name = "updated_at", columnDefinition = "datetime", nullable = false)
	private LocalDateTime updatedAt;
	@Column(name = "deleted_at", columnDefinition = "datetime")
	private LocalDateTime deletedAt;

	@Enumerated(EnumType.STRING)
	@Column(name = "type", columnDefinition = "varchar(8)", nullable = false)
	private NoticeType type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "food_id")
	private Food food;
}
