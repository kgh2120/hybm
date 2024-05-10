package com.dragontrain.md.domain.recipe.infra;

import com.dragontrain.md.domain.recipe.service.port.RecipeIngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@RequiredArgsConstructor
@Repository
public class RecipeIngredientRepositoryImpl implements RecipeIngredientRepository {
	private final JdbcTemplate template;

	@Autowired
	public RecipeIngredientRepositoryImpl(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}

	public void save(Integer recipeId, Integer ingredientId) {
		String sql = "INSERT INTO tbl_recipe_ingredient (recipe_id, ingredient_id) VALUES (?, ?)";
		template.update(sql, recipeId,ingredientId);
	}
}
