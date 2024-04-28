package com.dragontrain.md.domain.refrigerator.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "tbl_storage_design")
public class StorageDesign {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "storage_design_id", columnDefinition = "tinyint", nullable = false)
	private Integer storageDesignId;

	@Column(name = "storage_design_name", columnDefinition = "varchar(100)", nullable = false)
	private String storageDesignName;

	@Column(name = "level", columnDefinition = "int", nullable = false)
	private Integer level;

	@Column(name = "img_src", columnDefinition = "text", nullable = false)
	private String imgSrc;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storage_type", nullable = false)
	private StorageType storageType;
}
