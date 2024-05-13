package com.dragontrain.md.domain.refrigerator.service.port;

import java.util.List;
import java.util.Optional;

import com.dragontrain.md.domain.refrigerator.domain.StorageDesign;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;

public interface StorageDesignRepository {
	Optional<StorageDesign> findStorageDesignByLevelAndType(int level, StorageTypeId storageTypeId);

	List<StorageDesign> findNextStorageDesign(int originalLevel, int currentLevel);
}
