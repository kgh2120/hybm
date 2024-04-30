package com.dragontrain.md.domain.refrigerator.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
	private Badge badge;

	@MapsId("refrigeratorId")
	@ManyToOne(fetch = FetchType.LAZY)
	private Refrigerator refrigerator;

	@Column(name = "created_at", columnDefinition = "datetime", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "position", columnDefinition = "tinyint")
	private Integer position;

	@Column(name = "is_attached", columnDefinition = "boolean default false", nullable = false)
	private Boolean isAttached;


}
