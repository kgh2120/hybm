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
@Table(name = "tbl_storage_storage_design")
public class StorageStorageDesign {

	@EmbeddedId
	private StorageStorageDesignId storageStorageDesignId;

	@Column(name = "is_applied", columnDefinition = "boolean", nullable = false)
	private Boolean isApplied;

	@Column(name = "created_at", columnDefinition = "datetime", nullable = false)
	private LocalDateTime createdAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storage_type")
	private StorageType storageType;

	@MapsId("storageDesignId")
	@ManyToOne(fetch = FetchType.LAZY)
	private StorageDesign storageDesign;

	@MapsId("refrigeratorId")
	@ManyToOne(fetch = FetchType.LAZY)
	private Refrigerator refrigerator;

}
