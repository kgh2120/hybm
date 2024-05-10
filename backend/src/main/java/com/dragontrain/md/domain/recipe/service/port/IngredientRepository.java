package com.dragontrain.md.domain.recipe.service.port;


public interface IngredientRepository {
	void save(String name, Integer categoryDetailId);
//	boolean existsByName(String name);
}
