package com.dragontrain.md.domain.refrigerator.domain;

import java.time.LocalDateTime;

import com.dragontrain.md.domain.food.domain.Food;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorErrorCode;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorException;
import com.dragontrain.md.domain.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@Table(name = "tbl_refrigerator")
public class Refrigerator {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "refrigerator_id", columnDefinition = "bigint", nullable = false)
	private Long refrigeratorId;

	@Column(name = "exp", columnDefinition = "int default 0", nullable = false)
	private Integer exp;

	@Column(name = "created_at", columnDefinition = "datetime", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", columnDefinition = "datetime", nullable = false)
	private LocalDateTime updatedAt;

	@Column(name = "deleted_at", columnDefinition = "datetime")
	private LocalDateTime deletedAt;

	@Column(name = "is_deleted", columnDefinition = "boolean default false", nullable = false)
	private Boolean isDeleted;

	@JoinColumn(name = "user_id")
	@OneToOne(fetch = FetchType.LAZY)
	private User user;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "level_id")
	private Level level;

	public static Refrigerator create(User user, Level level, LocalDateTime now) {
		return Refrigerator.builder()
			.exp(0)
			.createdAt(now)
			.updatedAt(now)
			.isDeleted(false)
			.user(user)
			.level(level)
			.build();
	}

	public boolean isMyFood(Food food) {
		return equals(food.getRefrigerator());
	}

	public void delete(LocalDateTime now) {
		if (isDeleted) {
			throw new RefrigeratorException(RefrigeratorErrorCode.ALREADY_DELETED_REFRIGERATOR);
		}
		this.updatedAt = now;
		this.deletedAt = now;
		this.isDeleted = true;
	}
}
