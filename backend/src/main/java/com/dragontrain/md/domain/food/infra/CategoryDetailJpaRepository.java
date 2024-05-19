package com.dragontrain.md.domain.food.infra;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dragontrain.md.domain.food.domain.CategoryDetail;

public interface CategoryDetailJpaRepository extends JpaRepository<CategoryDetail, Integer> {

	Optional<CategoryDetail> findByKanCode(Integer kanCode);

	List<CategoryDetail> findAllByName(String name);

	Boolean existsByName(String name);

	Boolean existsByCategoryDetailId(Integer categoryDetailId);
}
