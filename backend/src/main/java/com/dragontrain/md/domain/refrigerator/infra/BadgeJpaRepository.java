package com.dragontrain.md.domain.refrigerator.infra;

import com.dragontrain.md.domain.refrigerator.domain.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BadgeJpaRepository extends JpaRepository<Badge, Integer> {

	Optional<Badge> findByCategoryBig_CategoryBigId(Integer categoryBigId);
}
