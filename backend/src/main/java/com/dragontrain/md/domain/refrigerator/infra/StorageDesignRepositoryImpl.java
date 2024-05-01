package com.dragontrain.md.domain.refrigerator.infra;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.dragontrain.md.domain.refrigerator.domain.StorageDesign;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;
import com.dragontrain.md.domain.refrigerator.service.port.StorageDesignRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class StorageDesignRepositoryImpl implements StorageDesignRepository {
	private final StorageDesignJpaRepository storageDesignJpaRepository;
	@Override
	public Optional<StorageDesign> findStorageDesignByLevelAndType(int level, StorageTypeId storageTypeId) {
		return storageDesignJpaRepository.findByLevelAndStorageType_StorageType(level, storageTypeId);
	}
}
