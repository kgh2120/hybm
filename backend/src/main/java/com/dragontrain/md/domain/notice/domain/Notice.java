package com.dragontrain.md.domain.notice.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import com.dragontrain.md.domain.food.domain.Food;
import com.dragontrain.md.domain.food.domain.FoodStatus;
import com.dragontrain.md.domain.notice.exception.NoticeErrorCode;
import com.dragontrain.md.domain.notice.exception.NoticeException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
	@Column(name = "type", columnDefinition = "varchar(9)", nullable = false)
	private NoticeType type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "food_id")
	private Food food;

	public static Notice create(Food food, String content, LocalDateTime now) {
		return Notice.builder()
			.food(food)
			.isChecked(false)
			.createdAt(now)
			.updatedAt(now)
			.content(content)
			.type(food.getFoodStatus().equals(FoodStatus.DANGER) ? NoticeType.TO_DANGER : NoticeType.TO_ROTTEN)
			.build();
	}

	public boolean isDeleted() {
		return Objects.nonNull(this.deletedAt);
	}

	public void delete(LocalDateTime deleteTime) {
		if (isDeleted()) {
			throw new NoticeException(NoticeErrorCode.ALREADY_DELETED_NOTICE);
		}
		this.updatedAt = deleteTime;
		this.deletedAt = deleteTime;
	}
}
