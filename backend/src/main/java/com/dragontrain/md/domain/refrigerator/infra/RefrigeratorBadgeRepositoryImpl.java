package com.dragontrain.md.domain.refrigerator.infra;

import static com.dragontrain.md.domain.refrigerator.domain.QBadge.*;
import static com.dragontrain.md.domain.refrigerator.domain.QRefrigeratorBadge.*;

import com.dragontrain.md.domain.refrigerator.controller.response.BadgeInfo;
import com.dragontrain.md.domain.refrigerator.domain.QBadge;
import com.dragontrain.md.domain.refrigerator.domain.QRefrigeratorBadge;
import com.dragontrain.md.domain.refrigerator.domain.RefrigeratorBadge;
import com.dragontrain.md.domain.refrigerator.service.port.RefrigeratorBadgeRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Repository
public class RefrigeratorBadgeRepositoryImpl implements RefrigeratorBadgeRepository {
	private final RefrigeratorBadgeJpaRepository refrigeratorBadgeJpaRepository;
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public void save(RefrigeratorBadge refrigeratorBadge) {
		refrigeratorBadgeJpaRepository.save(refrigeratorBadge);
	}

	@Override
	public List<BadgeInfo> findAllByRefrigeratorId(Long refrigeratorId) {

		// @Query("select new com.dragontrain.md.domain.refrigerator.controller.response.BadgeInfo(b.badgeId, b.badgeName, b.imgSrc, b.badgeRequire, CASE when rb is null then null else rb.isAttached end, "
		// 	+ " case when rb is null then null else rb.position end ) "
		// 	+ "from RefrigeratorBadge rb right join rb.badge b where rb.refrigerator.refrigeratorId=:refrigeratorId")
		// List<BadgeInfo> findAllBadges(Long refrigeratorId);


		return jpaQueryFactory.select(Projections.constructor(BadgeInfo.class,badge.badgeId, badge.badgeName, badge.imgSrc, badge.badgeRequire,
				 refrigeratorBadge.isAttached, refrigeratorBadge.position))
			.from(refrigeratorBadge)
			.rightJoin(refrigeratorBadge.badge, badge)
			.on(refrigeratorBadge.refrigerator.refrigeratorId.eq(refrigeratorId))
			.fetch();


		// return refrigeratorBadgeJpaRepository.findAllBadges(refrigeratorId);
	}

	@Override
	public List<RefrigeratorBadge> findAllAttachedBadges(Long refrigeratorId) {
		return refrigeratorBadgeJpaRepository.findAllAttachedBadges(refrigeratorId);
	}

	@Override
	public Optional<RefrigeratorBadge> findByBadgeId(Long refrigeratorId, Integer badgeId) {
		return refrigeratorBadgeJpaRepository.findByRefrigeratorBadgeId_RefrigeratorIdAndRefrigeratorBadgeId_BadgeId(
			refrigeratorId, badgeId
		);
	}

	@Override
	public Optional<RefrigeratorBadge> findByPosition(Long refrigeratorId, Integer position) {
		return refrigeratorBadgeJpaRepository.findByRefrigeratorBadgeId_RefrigeratorIdAndPosition(refrigeratorId, position);
	}

	@Override
	public Boolean existsByBadgeId(Long refrigeratorId, Integer badgeId) {
		return refrigeratorBadgeJpaRepository.existsByRefrigeratorBadgeId_RefrigeratorIdAndRefrigeratorBadgeId_BadgeId(refrigeratorId, badgeId);
	}
}
