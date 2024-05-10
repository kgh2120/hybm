package com.dragontrain.md.domain.recipe.domain;

import com.dragontrain.md.domain.food.domain.CategoryDetail;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_ingredient")
public class Ingredient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ingredient_id", columnDefinition = "int", nullable = false)
	private Integer ingredientId;

	@Column(name = "name", columnDefinition = "varchar(64)", nullable = false)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_detail_id")
	private CategoryDetail categoryDetail;
}
