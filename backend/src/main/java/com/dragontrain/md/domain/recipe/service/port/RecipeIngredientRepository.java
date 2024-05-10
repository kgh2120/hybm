package com.dragontrain.md.domain.recipe.service.port;

public interface RecipeIngredientRepository {
	void save(Integer recipeId, Integer ingredientId);
}
