package com.dragontrain.md.domain.refrigerator.infra;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.dragontrain.md.domain.refrigerator.domain.Level;
import com.dragontrain.md.domain.refrigerator.service.LevelRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class LevelRepositoryImpl implements LevelRepository {

	private final JpaLevelRepository jpaLevelRepository;

	@Override
	public Optional<Level> findLevel(int level) {
		return jpaLevelRepository.findByLevel(level);
	}
}
