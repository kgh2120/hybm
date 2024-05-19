package com.dragontrain.md.domain.refrigerator.infra;

import com.dragontrain.md.domain.refrigerator.controller.response.BadgeInfo;
import com.dragontrain.md.domain.refrigerator.domain.RefrigeratorBadge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RefrigeratorBadgeJpaRepository extends JpaRepository<RefrigeratorBadge, Long> {


	// @Query("select new com.dragontrain.md.domain.refrigerator.controller.response.BadgeInfo(b.badgeId, b.badgeName, b.imgSrc, b.badgeRequire, CASE when rb is null then null else rb.isAttached end, "
	// 	+ " case when rb is null then null else rb.position end ) "
	// 	+ "from RefrigeratorBadge rb right join rb.badge b where rb.refrigerator.refrigeratorId=:refrigeratorId")
	// List<BadgeInfo> findAllBadges(Long refrigeratorId);

	List<RefrigeratorBadge> findAllByRefrigerator_RefrigeratorId(Long refrigeratorId);

	@Query("SELECT rb FROM RefrigeratorBadge rb WHERE rb.refrigeratorBadgeId.refrigeratorId = :refrigeratorId" +
	" AND rb.isAttached = TRUE ORDER BY rb.position")
	List<RefrigeratorBadge> findAllAttachedBadges(Long refrigeratorId);

	Optional<RefrigeratorBadge> findByRefrigeratorBadgeId_RefrigeratorIdAndRefrigeratorBadgeId_BadgeId(Long refrigeratorId, Integer badgeId);

	Optional<RefrigeratorBadge> findByRefrigeratorBadgeId_RefrigeratorIdAndPosition(Long refrigeratorId, Integer position);

	Boolean existsByRefrigeratorBadgeId_RefrigeratorIdAndRefrigeratorBadgeId_BadgeId(Long refrigeratorId, Integer badgeId);
}
