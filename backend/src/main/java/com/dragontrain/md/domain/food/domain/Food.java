package com.dragontrain.md.domain.food.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.dragontrain.md.domain.food.exception.FoodErrorCode;
import com.dragontrain.md.domain.food.exception.FoodException;
import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;
import com.dragontrain.md.domain.refrigerator.domain.StorageType;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

	@Enumerated(EnumType.STRING)
	@Column(name = "status", columnDefinition = "varchar(7)", nullable = false)
	private FoodStatus foodStatus;

	public static Food create(String name, CategoryDetail categoryDetail,
		Integer price, LocalDate expectedExpirationDate, StorageTypeId storageType,
		Refrigerator refrigerator,
		LocalDateTime now, Boolean isManual) {
		return Food.builder()
			.name(name)
			.isManual(isManual)
			.categoryDetail(categoryDetail)
			.createdAt(now)
			.updatedAt(now)
			.storageType(StorageType.builder().storageType(storageType).build())
			.isManual(true)
			.price(price)
			.expectedExpirationDate(expectedExpirationDate)
			.foodStatus(calculateFoodStatus(expectedExpirationDate, now.toLocalDate()))
			.refrigerator(refrigerator)
			.build();
	}

	public Food update(String name,
		CategoryDetail categoryDetail,
		Integer price,
		LocalDate expectedExpirationDate,
		StorageTypeId storageType,
		LocalDateTime updatedAt) {

		this.name = name;
		this.categoryDetail = categoryDetail;
		this.price = price;
		this.expectedExpirationDate = expectedExpirationDate;
		this.foodStatus = calculateFoodStatus(expectedExpirationDate, updatedAt.toLocalDate());
		this.storageType = StorageType.builder().storageType(storageType).build();
		this.updatedAt = updatedAt;
		return this;
	}

	public void clear() {
		this.deletedAt = LocalDateTime.now();
		this.foodDeleteType = FoodDeleteType.CLEAR;
	}

	private static FoodStatus calculateFoodStatus(LocalDate expectedExpirationDate, LocalDate now) {
		long days = ChronoUnit.DAYS.between(now, expectedExpirationDate);
		if (days <= 0) {
			return FoodStatus.ROTTEN;
		} else if (days <= 3) {
			return FoodStatus.DANGER;
		} else if (days <= 7) {
			return FoodStatus.WARNING;
		}
		return FoodStatus.FRESH;
	}

	public Integer getDDay(LocalDate expectedExpirationDate, LocalDate now) {
		return (int)ChronoUnit.DAYS.between(expectedExpirationDate, now);
	}

	public boolean isDeleted() {
		return Objects.nonNull(deletedAt);
	}

	public void delete(FoodDeleteType foodDeleteType, LocalDateTime localDateTime) {
		if (!Objects.isNull(deletedAt)) {
			throw new FoodException(FoodErrorCode.ALREADY_DELETED_FOOD);
		}

		this.deletedAt = localDateTime;
		this.foodDeleteType = foodDeleteType;
	}

	public void changeStatus(FoodStatus foodStatus, LocalDateTime localDateTime) {
		if (!Objects.isNull(deletedAt)) {
			throw new FoodException(FoodErrorCode.ALREADY_DELETED_FOOD);
		}

		if (this.foodStatus.equals(foodStatus)) {
			throw new FoodException(FoodErrorCode.ALREADY_THAT_STATUS);
		}
		this.foodStatus = foodStatus;
		this.updatedAt = localDateTime;
	}
}
