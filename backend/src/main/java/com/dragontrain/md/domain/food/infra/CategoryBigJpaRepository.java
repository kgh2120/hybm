package com.dragontrain.md.domain.food.infra;

import com.dragontrain.md.domain.food.domain.CategoryBig;
import com.dragontrain.md.domain.statistics.service.dto.BigCategoryPriceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryBigJpaRepository extends JpaRepository<CategoryBig, Integer> {
	@Query("select new com.dragontrain.md.domain.statistics.service.dto.BigCategoryPriceInfo" +
		"(cb.name, f.price)" +
		" from Food f join f.categoryDetail cd join cd.categoryBig cb" +
		" on f.refrigerator.refrigeratorId=:refrigeratorId and f.categoryDetail=cd and cd.categoryBig=cb" +
		" where year(f.createdAt)=:year and month(f.createdAt)=:month")
	List<BigCategoryPriceInfo> findAllBigGroupAndSpend(Long refrigeratorId, Integer year, Integer month);

}
