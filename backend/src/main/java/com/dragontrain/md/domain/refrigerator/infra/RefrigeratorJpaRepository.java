package com.dragontrain.md.domain.refrigerator.infra;

import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefrigeratorJpaRepository extends JpaRepository<Refrigerator, Long> {
	Optional<Refrigerator> findByUser_UserId(Long userId);
}
