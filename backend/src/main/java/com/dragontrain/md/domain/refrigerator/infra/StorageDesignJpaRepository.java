package com.dragontrain.md.domain.refrigerator.infra;

import com.dragontrain.md.domain.refrigerator.controller.response.StorageDesignResponse;
import com.dragontrain.md.domain.refrigerator.domain.StorageDesign;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StorageDesignJpaRepository extends JpaRepository<StorageDesign, Short> {
	Optional<StorageDesign> findByLevelAndStorageType_StorageType(Integer level, StorageTypeId storageType);

	Optional<StorageDesign> findByStorageDesignId(Integer storageDesignId);
}
