package com.dragontrain.md.domain.refrigerator.domain;

import jakarta.persistence.*;
import lombok.*;

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
