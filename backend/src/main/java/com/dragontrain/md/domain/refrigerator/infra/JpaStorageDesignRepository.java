package com.dragontrain.md.domain.refrigerator.infra;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dragontrain.md.domain.refrigerator.domain.StorageDesign;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;

public interface JpaStorageDesignRepository extends JpaRepository<StorageDesign, Integer> {

	Optional<StorageDesign> findByLevelAndStorageType_StorageType(Integer level, StorageTypeId storageType);
}
