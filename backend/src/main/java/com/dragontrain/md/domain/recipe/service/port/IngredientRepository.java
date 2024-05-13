package com.dragontrain.md.domain.recipe.service.port;


import com.dragontrain.md.domain.recipe.domain.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository {
	void save(String name, Integer categoryDetailId);
	boolean existsByName(String name);
	Optional<Ingredient> findByCategoryDetailId(Integer categoryDetailId);
	List<Ingredient> findByName(String name);
}
