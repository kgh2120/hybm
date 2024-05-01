package com.dragontrain.md.domain.refrigerator.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_level")
public class Level {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "level_id", columnDefinition = "tinyint", nullable = false)
	private Integer levelId;

	@Column(name = "max_exp", columnDefinition = "int", nullable = false)
	private Integer maxExp;

	@Column(name = "level", columnDefinition = "tinyint", nullable = false)
	private Integer level;
}
