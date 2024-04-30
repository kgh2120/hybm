package com.dragontrain.md.domain.refrigerator.infra;

import com.dragontrain.md.domain.refrigerator.controller.Response.AppliedStorageDesign;
import com.dragontrain.md.domain.refrigerator.controller.Response.StorageDesignResponse;
import com.dragontrain.md.domain.refrigerator.domain.StorageStorageDesign;
import com.dragontrain.md.domain.refrigerator.service.port.StorageStorageDesignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class StorageStorageDesignRepositoryImpl implements StorageStorageDesignRepository {

	private final StorageStorageDesignJpaRepository storageStorageDesignJpaRepository;

	@Override
	public List<StorageDesignResponse> findAllStorageDesign(Long refrigeratorId) {
		return storageStorageDesignJpaRepository.findAllStorageDesign(refrigeratorId);
	}

	@Override
	public List<AppliedStorageDesign> findAllAppliedStorageDesign(Long refrigeratorId) {
		return storageStorageDesignJpaRepository.findAllAppliedStorageDesign(refrigeratorId);
	}
}
