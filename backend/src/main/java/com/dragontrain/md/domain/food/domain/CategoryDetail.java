package com.dragontrain.md.domain.food.domain;

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
@Table(name = "tbl_category_detail")
public class CategoryDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_detail_id", columnDefinition = "int", nullable = false)
	private Integer categoryDetailId;

	@Column(name = "name", columnDefinition = "varchar(20)", nullable = false)
	private String name;

	@Column(name = "img_src", columnDefinition = "text", nullable = false)
	private String imgSrc;

	@Column(name = "expiration_date", columnDefinition = "int", nullable = false)
	private Integer expirationDate;

	@Column(name = "kan_code", columnDefinition = "int", unique = true)
	private Integer kanCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_big_id")
	private CategoryBig categoryBig;
}
