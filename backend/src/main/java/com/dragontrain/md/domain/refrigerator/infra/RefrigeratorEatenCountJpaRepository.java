package com.dragontrain.md.domain.refrigerator.infra;

import com.dragontrain.md.domain.refrigerator.domain.RefrigeratorEatenCount;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RefrigeratorEatenCountJpaRepository extends JpaRepository<RefrigeratorEatenCount, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT rec FROM RefrigeratorEatenCount rec" +
		" WHERE rec.refrigeratorCategoryBigId.refrigeratorId = :refrigeratorId" +
		" AND rec.refrigeratorCategoryBigId.categoryBigId = :categoryBigId")
	Optional<RefrigeratorEatenCount> findByRefrigeratorIdAndCategoryBigId(Long refrigeratorId, Integer categoryBigId);
}
