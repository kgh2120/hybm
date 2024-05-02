package com.dragontrain.md.domain.food.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_category_big")
public class CategoryBig {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_big_id", columnDefinition = "tinyint", nullable = false)
	private Integer categoryBigId;

	@Column(name = "name", columnDefinition = "varchar(20)", nullable = false, unique = true)
	private String name;

	@Column(name = "img_src", columnDefinition = "text", nullable = false)
	private String imgSrc;

	@OneToMany(mappedBy = "categoryBig")
	private List<CategoryDetail> categoryDetails;
}
