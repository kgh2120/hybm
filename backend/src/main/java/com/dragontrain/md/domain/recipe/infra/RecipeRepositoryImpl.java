package com.dragontrain.md.domain.recipe.infra;

import com.dragontrain.md.domain.recipe.domain.Ingredient;
import com.dragontrain.md.domain.recipe.domain.Recipe;
import com.dragontrain.md.domain.recipe.service.port.RecipeRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static com.dragontrain.md.domain.recipe.domain.QRecipe.recipe;


@RequiredArgsConstructor
@Repository
public class RecipeRepositoryImpl implements RecipeRepository {

	private final JdbcTemplate template;
	private final JPAQueryFactory jpaQueryFactory;

	@Autowired
	public RecipeRepositoryImpl(DataSource dataSource, JPAQueryFactory jpaQueryFactory) {
		this.template = new JdbcTemplate(dataSource);
		this.jpaQueryFactory = jpaQueryFactory;
	}

	@Override
	public void save(String name, String author){
		String sql = "INSERT INTO tbl_recipe(name, author) VALUES (?, ?)";
		template.update(sql, name, author);
	}
}
