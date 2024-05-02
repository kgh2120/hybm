package com.dragontrain.md.domain.food.infra;

import com.dragontrain.md.domain.food.domain.CategoryBig;
import com.dragontrain.md.domain.food.service.port.CategoryBigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CategoryBigRepositoryImpl implements CategoryBigRepository {

	private final CategoryBigJpaRepository categoryBigJpaRepository;

	@Override
	public List<CategoryBig> findAll() {

		return categoryBigJpaRepository.findAll();
	}
}
