package com.dragontrain.md.domain.food.infra;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dragontrain.md.domain.food.domain.CategoryDetail;

public interface CategoryDetailJpaRepository extends JpaRepository<CategoryDetail, Integer> {

	Optional<CategoryDetail> findByKanCode(Integer kanCode);
}
