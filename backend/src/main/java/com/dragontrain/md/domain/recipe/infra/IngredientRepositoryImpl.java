package com.dragontrain.md.domain.recipe.infra;


import com.dragontrain.md.domain.recipe.domain.Ingredient;
import com.dragontrain.md.domain.recipe.service.port.IngredientRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static com.dragontrain.md.domain.recipe.domain.QIngredient.ingredient;

@RequiredArgsConstructor
@Repository
public class IngredientRepositoryImpl implements IngredientRepository {
	private final JdbcTemplate template;
	private final JPAQueryFactory jpaQueryFactory;

	@Autowired
	public IngredientRepositoryImpl(DataSource dataSource, JPAQueryFactory jpaQueryFactory) {
		this.template = new JdbcTemplate(dataSource);
		this.jpaQueryFactory = jpaQueryFactory;
	}

	@Override
	public void save(String name, Integer categoryDetailId){
		String sql = "INSERT INTO tbl_ingredient(ingredient_name, category_detail_id) VALUES (?, ?)";
		template.update(sql, name, categoryDetailId);
	}

	@Override
	public boolean existsByName(String name) {
		String sql = "SELECT * FROM tbl_ingredient WHERE ingredient_name = ?";
		return template.query(sql, (rs, rowNum) -> 0, name).isEmpty();
	}

	@Override
	public Optional<Ingredient> findByCategoryDetailId(Integer categoryDetailId) {
		return Optional.ofNullable(jpaQueryFactory.selectFrom(ingredient)
			.where(ingredient.categoryDetail.categoryDetailId.eq(categoryDetailId))
			.fetchOne());
	}

	@Override
	public List<Ingredient> findByName(String name) {
		return jpaQueryFactory.selectFrom(ingredient)
			.where(ingredient.name.eq(name))
			.fetch();
	}

}
