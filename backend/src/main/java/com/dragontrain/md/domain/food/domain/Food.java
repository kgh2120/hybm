package com.dragontrain.md.domain.food.domain;

import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;
import com.dragontrain.md.domain.refrigerator.domain.StorageType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_food")
public class Food {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "food_id", columnDefinition = "bigint", nullable = false)
	private Long foodId;

	@Column(name = "name", columnDefinition = "varchar(100)", nullable = false)
	private String name;

	@Column(name = "expected_expiration_date", columnDefinition = "date", nullable = false)
	private LocalDate expectedExpirationDate;

	@Column(name = "created_at", columnDefinition = "datetime", nullable = false)
	private LocalDateTime createdAt;
	@Column(name = "updated_at", columnDefinition = "datetime", nullable = false)
	private LocalDateTime updatedAt;
	@Column(name = "deleted_at", columnDefinition = "datetime")
	private LocalDateTime deletedAt;

	@Enumerated(EnumType.STRING)
	@Column(name = "delete_type", columnDefinition = "varchar(10)")
	private FoodDeleteType foodDeleteType;

	@Column(name = "is_manual", columnDefinition = "boolean", nullable = false)
	private Boolean isManual;

	@Column(name = "price", columnDefinition = "int")
	private Integer price;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_detail_id")
	private CategoryDetail categoryDetail;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "refrigerator_id")
	private Refrigerator refrigerator;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storage_type")
	private StorageType storageType;

	@Column(name = "status", columnDefinition = "varchar(7)", nullable = false)
	private FoodStatus foodStatus;
}
