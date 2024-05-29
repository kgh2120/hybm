package com.dragontrain.md.domain.refrigerator.infra;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;

public interface RefrigeratorJpaRepository extends JpaRepository<Refrigerator, Long> {
	Optional<Refrigerator> findByUser_UserId(Long userId);
}
