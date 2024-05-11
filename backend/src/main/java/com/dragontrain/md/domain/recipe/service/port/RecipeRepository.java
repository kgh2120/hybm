package com.dragontrain.md.domain.recipe.service.port;


public interface RecipeRepository {
	void save(Integer id, String name, String author);
}
