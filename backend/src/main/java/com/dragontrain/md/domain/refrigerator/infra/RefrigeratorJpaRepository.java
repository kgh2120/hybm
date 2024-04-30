package com.dragontrain.md.domain.refrigerator.infra;

import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefrigeratorJpaRepository extends JpaRepository<Refrigerator, Long> {
	@Query("select r from Refrigerator r where r.user.userId=:userId")
	Optional<Refrigerator> findByUserId(Long userId);
}
