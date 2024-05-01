package com.dragontrain.md.domain.refrigerator.infra;

import org.springframework.stereotype.Repository;

import com.dragontrain.md.domain.refrigerator.domain.StorageStorageDesign;
import com.dragontrain.md.domain.refrigerator.service.StorageStorageDesignRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class StorageStorageDesignRepositoryImpl implements StorageStorageDesignRepository {

	private final JpaStorageStorageDesignRepository jpaStorageStorageDesignRepository;
	@Override
	public void save(StorageStorageDesign storageStorageDesign) {
		jpaStorageStorageDesignRepository.save(storageStorageDesign);
	}
}
