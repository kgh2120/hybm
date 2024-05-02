package com.dragontrain.md.domain.refrigerator.service.port;

import com.dragontrain.md.domain.refrigerator.domain.StorageType;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;

import java.util.Optional;

public interface StorageTypeRepository {
	Optional<StorageType> findById(StorageTypeId id);
}
