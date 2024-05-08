package com.dragontrain.md.domain.refrigerator.infra;

import com.dragontrain.md.domain.refrigerator.domain.Badge;
import com.dragontrain.md.domain.refrigerator.service.port.BadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class BadgeRepositoryImpl implements BadgeRepository {
	private final BadgeJpaRepository badgeJpaRepository;
	@Override
	public Optional<Badge> findBadgeByCategoryBigId(Integer categoryBigId) {
		return badgeJpaRepository.findByCategoryBig_CategoryBigId(categoryBigId);
	}
}
