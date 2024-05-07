package com.dragontrain.md.domain.recipe.domain;

import com.dragontrain.md.domain.food.domain.CategoryDetail;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
@Table(name = "tbl_recipe_food")
public class RecipeFood {

	@EmbeddedId
	private RecipeFoodId recipeFoodId;

	@Column(name = "ingredient_name", columnDefinition = "varchar(100)", nullable = false)
	private String ingredientName;

	@MapsId("recipeId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipe_id", columnDefinition = "int")
	private Recipe recipe;

	@MapsId("categoryDetailId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_detail_id", columnDefinition = "int")
	private CategoryDetail categoryDetail;
}
