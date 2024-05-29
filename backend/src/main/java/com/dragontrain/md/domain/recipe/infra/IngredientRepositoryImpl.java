package com.dragontrain.md.domain.recipe.infra;


import com.dragontrain.md.domain.recipe.domain.Ingredient;
import com.dragontrain.md.domain.food.domain.CategoryDetail;
import com.dragontrain.md.domain.recipe.domain.Ingredient;
import com.dragontrain.md.domain.recipe.domain.QIngredient;
import com.dragontrain.md.domain.recipe.service.port.IngredientRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static com.dragontrain.md.domain.recipe.domain.QIngredient.ingredient;
import java.sql.PreparedStatement;
import java.sql.Types;


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
	public void save(String name, Integer categoryDetailId) {
		String sql = "INSERT INTO tbl_ingredient(ingredient_name, category_detail_id) VALUES (?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(con -> {
			PreparedStatement ps = con.prepareStatement(sql, new String[]{"ingredient_id"});
			ps.setString(1, name);
			if (categoryDetailId != null) {
				ps.setInt(2, categoryDetailId);
			} else {
				ps.setNull(2, Types.INTEGER);
			}

			return ps;
		}, keyHolder);
		//return Objects.requireNonNull(keyHolder.getKey()).intValue()
	}

	@Override
	public boolean existsByName(String name) {
		String sql = "SELECT * FROM tbl_ingredient WHERE ingredient_name = ?";
		return !template.query(sql, (rs, rowNum) -> 0, name).isEmpty();
	}

	@Override
	public List<Integer> findAllIngredientIdsByName(String name) {
		String sql = "SELECT * FROM tbl_ingredient WHERE ingredient_name = ?";
		List<Integer> ingredientIds = template.query(sql, (rs, rowNum) -> {
			Ingredient newIngredient = Ingredient.builder()
				.name(rs.getString("ingredient_name"))
				.ingredientId(rs.getInt("ingredient_id"))
				.build();
			return newIngredient.getIngredientId();
		}, name);
		return ingredientIds;
	}

	@Override
	public Optional<Ingredient> findByIngredientNameAndCategoryDetailId(
		String name,
		Integer categoryDetailId
	) {
//		String sql = "SELECT * FROM tbl_ingredient WHERE ingredient_name = ?" +
//			" AND category_detail.category_detail_id = ?";
//		Ingredient ingredient = template.queryForObject(
//			sql, ingredientRowMapper(), name, categoryDetailId
//		);
		JPAQuery<Ingredient> query = jpaQueryFactory.selectFrom(ingredient)
			.where(ingredient.name.eq(name));
		if (categoryDetailId != null) {
			query.where(ingredient.categoryDetail.categoryDetailId.eq(categoryDetailId));
		}
		return Optional.ofNullable(query.fetchFirst());
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
	private RowMapper<Ingredient> ingredientRowMapper() {
		return ((rs, rowNum) -> Ingredient.builder()
			.ingredientId(rs.getInt("ingredient_id"))
			.name(rs.getString("ingredient_name"))
			.categoryDetail(
				CategoryDetail.builder()
					.categoryDetailId(rs.getInt("category_detail_id"))
					.build()
			)
			.build()
		);
	}

}
