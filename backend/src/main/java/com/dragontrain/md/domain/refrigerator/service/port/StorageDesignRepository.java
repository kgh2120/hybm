package com.dragontrain.md.domain.refrigerator.service.port;

import java.util.Optional;

import com.dragontrain.md.domain.refrigerator.domain.StorageDesign;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;

public interface StorageDesignRepository {
	Optional<StorageDesign> findStorageDesignByLevelAndType(int level, StorageTypeId storageTypeId);
}
