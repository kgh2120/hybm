package com.dragontrain.md.domain.refrigerator.infra;

import com.dragontrain.md.domain.refrigerator.domain.RefrigeratorBadge;
import com.dragontrain.md.domain.refrigerator.service.port.RefrigeratorBadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


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
}
