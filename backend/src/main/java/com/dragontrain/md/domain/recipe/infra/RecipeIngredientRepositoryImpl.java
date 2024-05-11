package com.dragontrain.md.domain.recipe.infra;

import com.dragontrain.md.domain.food.domain.CategoryDetail;
import com.dragontrain.md.domain.recipe.domain.Ingredient;
import com.dragontrain.md.domain.recipe.service.port.RecipeIngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;

@RequiredArgsConstructor
@Repository
public class RecipeIngredientRepositoryImpl implements RecipeIngredientRepository {
	private final JdbcTemplate template;

	@Autowired
	public RecipeIngredientRepositoryImpl(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}

	@Override
	public void save(Integer recipeId, Integer ingredientId) {
		String sql = "INSERT INTO tbl_recipe_ingredient (recipe_id, ingredient_id) VALUES (?, ?)";
		template.update(sql, recipeId, ingredientId);
	}

	@Override
	public boolean existsById(Integer recipeId, Integer ingredientId) {
		String sql = "SELECT * FROM tbl_recipe_ingredient WHERE recipe_id = ? AND ingredient_id = ?";
		return !template.query(sql, (rs, rowNum) -> 0, recipeId, ingredientId).isEmpty();
	}

}
