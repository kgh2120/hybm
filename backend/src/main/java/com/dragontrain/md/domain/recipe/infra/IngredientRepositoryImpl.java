package com.dragontrain.md.domain.recipe.infra;

import com.dragontrain.md.domain.recipe.service.port.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@RequiredArgsConstructor
@Repository
public class IngredientRepositoryImpl implements IngredientRepository {
	private final JdbcTemplate template;

	@Autowired
	public IngredientRepositoryImpl(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}

	@Override
	public void save(String name, Integer categoryDetailId){
		String sql = "INSERT INTO tbl_ingredient(ingredient_name, category_detail_id) VALUES (?, ?)";
		template.update(sql, name, categoryDetailId);
	}

//	@Override
//	public boolean existsByName(String name) {
//		String sql = "SELECT * FROM tbl_ingredient WHERE ingredient_name = ?";
//	}

	;

}
