package com.dragontrain.md.domain.refrigerator.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "tbl_storage_type")
public class StorageType {

	@Id
	@Enumerated(EnumType.STRING)
	@Column(name = "storage_type", columnDefinition = "varchar(10)", nullable = false)
	private StorageTypeId storageType;

	@Column(name = "type_name", columnDefinition = "varchar(10)", nullable = false)
	private String typeName;

}
