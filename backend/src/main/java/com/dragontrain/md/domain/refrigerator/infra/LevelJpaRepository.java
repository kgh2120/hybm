package com.dragontrain.md.domain.refrigerator.infra;

import com.dragontrain.md.domain.refrigerator.domain.Level;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LevelJpaRepository extends JpaRepository<Level, Integer> {
	Optional<Level> findByLevel(int level);
}
