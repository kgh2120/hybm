package com.dragontrain.md.domain.refrigerator.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dragontrain.md.domain.refrigerator.domain.StorageStorageDesign;
import com.dragontrain.md.domain.refrigerator.domain.StorageStorageDesignId;

public interface JpaStorageStorageDesignRepository extends JpaRepository<StorageStorageDesign, StorageStorageDesignId> {
}
