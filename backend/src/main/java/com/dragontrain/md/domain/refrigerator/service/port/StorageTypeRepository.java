package com.dragontrain.md.domain.refrigerator.service.port;

import java.util.Optional;

import com.dragontrain.md.domain.refrigerator.domain.StorageType;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;

public interface StorageTypeRepository {
	Optional<StorageType> findById(StorageTypeId id);
}
