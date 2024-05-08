package com.dragontrain.md.domain.refrigerator.infra;

import com.dragontrain.md.domain.refrigerator.domain.RefrigeratorBadge;
import com.dragontrain.md.domain.refrigerator.service.port.RefrigeratorBadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Repository
public class RefrigeratorBadgeRepositoryImpl implements RefrigeratorBadgeRepository {
	private final RefrigeratorBadgeJpaRepository refrigeratorBadgeJpaRepository;

	@Override
	public void save(RefrigeratorBadge refrigeratorBadge) {
		refrigeratorBadgeJpaRepository.save(refrigeratorBadge);
	}

	@Override
	public List<RefrigeratorBadge> findAllByRefrigeratorId(Long refrigeratorId) {
		return refrigeratorBadgeJpaRepository.findAllByRefrigerator_RefrigeratorId(refrigeratorId);
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
