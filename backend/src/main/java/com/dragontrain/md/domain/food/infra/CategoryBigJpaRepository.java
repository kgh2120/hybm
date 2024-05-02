package com.dragontrain.md.domain.food.infra;

import com.dragontrain.md.domain.food.domain.CategoryBig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryBigJpaRepository extends JpaRepository<CategoryBig, Integer> {

	@Query("SELECT c FROM CategoryBig c")
	List<CategoryBig> findAll();
}
