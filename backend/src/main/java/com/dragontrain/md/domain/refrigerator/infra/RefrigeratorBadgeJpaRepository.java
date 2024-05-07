package com.dragontrain.md.domain.refrigerator.infra;

import com.dragontrain.md.domain.refrigerator.domain.RefrigeratorBadge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefrigeratorBadgeJpaRepository extends JpaRepository<RefrigeratorBadge, Long> {
	List<RefrigeratorBadge> findAllByRefrigerator_RefrigeratorId(Long refrigeratorId);
}
