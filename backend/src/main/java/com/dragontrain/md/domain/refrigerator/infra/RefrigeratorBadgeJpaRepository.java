package com.dragontrain.md.domain.refrigerator.infra;

import com.dragontrain.md.domain.refrigerator.domain.RefrigeratorBadge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RefrigeratorBadgeJpaRepository extends JpaRepository<RefrigeratorBadge, Long> {
	List<RefrigeratorBadge> findAllByRefrigerator_RefrigeratorId(Long refrigeratorId);

	@Query("SELECT rb FROM RefrigeratorBadge rb WHERE rb.refrigeratorBadgeId.refrigeratorId = :refrigeratorId" +
	" AND rb.isAttached = TRUE ORDER BY rb.position")
	List<RefrigeratorBadge> findAllAttachedBadges(Long refrigeratorId);

	Optional<RefrigeratorBadge> findByRefrigeratorBadgeId_RefrigeratorIdAndRefrigeratorBadgeId_BadgeId(Long refrigeratorId, Integer badgeId);

	Optional<RefrigeratorBadge> findByRefrigeratorBadgeId_RefrigeratorIdAndPosition(Long refrigeratorId, Integer position);
}
