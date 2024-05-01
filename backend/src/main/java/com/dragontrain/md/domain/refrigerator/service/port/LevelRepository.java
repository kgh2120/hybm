package com.dragontrain.md.domain.refrigerator.service.port;

import java.util.Optional;

import com.dragontrain.md.domain.refrigerator.domain.Level;

public interface LevelRepository {

	Optional<Level> findLevel(int level);
}
