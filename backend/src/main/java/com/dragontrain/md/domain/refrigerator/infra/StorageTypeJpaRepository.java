package com.dragontrain.md.domain.refrigerator.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dragontrain.md.domain.refrigerator.domain.StorageType;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;

public interface StorageTypeJpaRepository extends JpaRepository<StorageType, StorageTypeId> {
}
