package com.dragontrain.md.domain.recipe.service.port;


import com.dragontrain.md.domain.recipe.domain.Recipe;

import java.util.List;

public interface RecipeRepository {
	void save(Integer id, String name, String author);
}
