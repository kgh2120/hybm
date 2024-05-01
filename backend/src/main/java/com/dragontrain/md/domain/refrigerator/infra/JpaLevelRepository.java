package com.dragontrain.md.domain.refrigerator.infra;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dragontrain.md.domain.refrigerator.domain.Level;

public interface JpaLevelRepository extends JpaRepository<Level, Integer> {

	Optional<Level> findByLevel(int level);
}
