package com.dragontrain.md.domain.recipe.infra;

import com.dragontrain.md.domain.recipe.service.port.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;


@RequiredArgsConstructor
@Repository
public class RecipeRepositoryImpl implements RecipeRepository {

	private final JdbcTemplate template;

	@Autowired
	public RecipeRepositoryImpl(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}

	@Override
	public void save(String name, String author){
		String sql = "INSERT INTO tbl_recipe(name, author) VALUES (?, ?)";
		template.update(sql, name, author);
	};
}
