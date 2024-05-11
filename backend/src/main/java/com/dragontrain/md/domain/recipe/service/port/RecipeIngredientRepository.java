package com.dragontrain.md.domain.recipe.service.port;

public interface RecipeIngredientRepository {

	void save(Integer recipeId, Integer ingredientId);
	boolean existsById(Integer recipeId, Integer ingredientId);
}
