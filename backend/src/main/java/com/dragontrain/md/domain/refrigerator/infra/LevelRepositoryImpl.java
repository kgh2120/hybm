package com.dragontrain.md.domain.refrigerator.infra;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.dragontrain.md.domain.refrigerator.domain.Level;
import com.dragontrain.md.domain.refrigerator.service.port.LevelRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class LevelRepositoryImpl implements LevelRepository {

	private final LevelJpaRepository levelJpaRepository;

	@Override
	public Optional<Level> findLevel(int level) {
		return levelJpaRepository.findByLevel(level);
	}

	@Override
	public Optional<Level> getNextLevel(int currentLevel) {
		return levelJpaRepository.findNextLevel(currentLevel);
	}
}
