package com.dragontrain.md.domain.food.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_barcode")
public class Barcode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "barcode_id", columnDefinition = "bigint", nullable = false)
	private Long barcodeId;

	@Column(name = "name", columnDefinition = "varchar(100)", nullable = false)
	private String name;

	@Column(name = "created_at", columnDefinition = "datetime", nullable = false)
	private LocalDateTime createdAt;
	@Column(name = "updated_at", columnDefinition = "datetime", nullable = false)
	private LocalDateTime updatedAt;
	@Column(name = "deleted_at", columnDefinition = "datetime")
	private LocalDateTime deletedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kan_code", referencedColumnName = "kan_code", nullable = false)
	private CategoryDetail categoryDetail;
}
