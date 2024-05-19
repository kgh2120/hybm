package com.dragontrain.md.domain.recipe.domain;

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
@Table(name = "tbl_recipe_ingredient")
public class RecipeIngredient {

	@EmbeddedId
	private RecipeIngredientId recipeIngredientId;

	@MapsId("recipeId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipe_id", columnDefinition = "int")
	private Recipe recipe;

	@MapsId("ingredientId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ingredient_id", columnDefinition = "int")
	private Ingredient ingredient;
}
