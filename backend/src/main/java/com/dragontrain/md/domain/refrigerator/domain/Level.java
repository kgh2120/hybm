package com.dragontrain.md.domain.refrigerator.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
