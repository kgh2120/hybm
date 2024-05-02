package com.dragontrain.md.domain.food.domain;

import jakarta.persistence.*;
import lombok.*;

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

	@Column(name = "name", columnDefinition = "varchar(20)", nullable = false, unique = true)
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
