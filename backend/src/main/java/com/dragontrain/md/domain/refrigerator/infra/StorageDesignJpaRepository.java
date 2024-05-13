package com.dragontrain.md.domain.refrigerator.infra;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dragontrain.md.domain.refrigerator.domain.StorageDesign;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;

public interface StorageDesignJpaRepository extends JpaRepository<StorageDesign, Short> {
	Optional<StorageDesign> findByLevelAndStorageType_StorageType(Integer level, StorageTypeId storageType);

	Optional<StorageDesign> findByStorageDesignId(Integer storageDesignId);

	@Query("select sd from StorageDesign sd where sd.level between :originalLevel+1 and :currentLevel ")
	List<StorageDesign> findNextStorageDesign(int originalLevel, int currentLevel);
}
