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
	@JoinColumn(name = "badge_id")
	private Badge badge;

	@MapsId("refrigeratorId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "refrigerator_id")
	private Refrigerator refrigerator;

	@Column(name = "created_at", columnDefinition = "datetime", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "position", columnDefinition = "tinyint")
	private Integer position;

	@Column(name = "is_attached", columnDefinition = "boolean default false", nullable = false)
	private Boolean isAttached;

}
