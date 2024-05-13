package com.dragontrain.md.domain.recipe.service.port;

import com.dragontrain.md.domain.recipe.domain.RecipeIngredient;

import java.util.List;

public interface RecipeIngredientRepository {
	void save(Integer recipeId, Integer ingredientId);
	List<RecipeIngredient> findByIngredientId(Integer ingredientId);
}
