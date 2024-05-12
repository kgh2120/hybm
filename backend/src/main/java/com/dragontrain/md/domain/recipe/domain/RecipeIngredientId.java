package com.dragontrain.md.domain.recipe.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Embeddable
public class RecipeIngredientId implements Serializable {

	@EqualsAndHashCode.Include
	@Column(name = "recipe_id", columnDefinition = "int")
	private Integer recipeId;

	@EqualsAndHashCode.Include
	@Column(name = "ingredient_id", columnDefinition = "int")
	private Integer ingredientId;
}
