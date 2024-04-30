package com.dragontrain.md.domain.refrigerator.domain;

import com.dragontrain.md.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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


}
