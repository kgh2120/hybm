package com.dragontrain.md.domain.refrigerator.infra;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dragontrain.md.domain.refrigerator.domain.Level;

public interface LevelJpaRepository extends JpaRepository<Level, Integer> {
	Optional<Level> findByLevel(int level);

	@Query("select l from Level l where l.level-1 =:currentLevel")
	Optional<Level> findNextLevel(int currentLevel);
}
