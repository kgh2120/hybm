package com.dragontrain.md.domain.refrigerator.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
@Table(name = "tbl_refrigerator_badge")
public class RefrigeratorBadge {

	@EmbeddedId
	private RefrigeratorBadgeId refrigeratorBadgeId;

	@MapsId("badgeId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "badge_id", columnDefinition = "smallint", nullable = false)
	private Badge badge;

	@MapsId("refrigeratorId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "refrigerator_id", columnDefinition = "bigint", nullable = false)
	private Refrigerator refrigerator;

	@Column(name = "created_at", columnDefinition = "datetime", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "position", columnDefinition = "tinyint")
	private Integer position;

	@Column(name = "is_attached", columnDefinition = "boolean default false", nullable = false)
	private Boolean isAttached;

	public static RefrigeratorBadge create(Refrigerator refrigerator, Badge badge) {
		return RefrigeratorBadge.builder()
			.refrigeratorBadgeId(RefrigeratorBadgeId.builder()
				.badgeId(badge.getBadgeId())
				.refrigeratorId(refrigerator.getRefrigeratorId())
				.build())
			.badge(badge)
			.refrigerator(refrigerator)
			.createdAt(LocalDateTime.now())
			.position(null)
			.isAttached(false)
			.build();
	}

	public void detachBadge() {
		this.position = null;
		this.isAttached = false;
	}

	public void attachBadge(Integer position) {
		this.position = position;
		this.isAttached = true;
	}
}
