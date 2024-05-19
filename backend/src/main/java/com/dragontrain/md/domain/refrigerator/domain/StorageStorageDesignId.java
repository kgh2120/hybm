package com.dragontrain.md.domain.refrigerator.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Embeddable
public class StorageStorageDesignId implements Serializable {

	@EqualsAndHashCode.Include
	@Column(name = "storage_design_id", columnDefinition = "tinyint")
	private Integer storageDesignId;
	@EqualsAndHashCode.Include
	@Column(name = "refrigerator_id", columnDefinition = "bigint")
	private Long refrigeratorId;
}
