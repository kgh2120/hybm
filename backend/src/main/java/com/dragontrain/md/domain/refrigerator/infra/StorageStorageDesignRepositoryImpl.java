package com.dragontrain.md.domain.refrigerator.infra;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dragontrain.md.domain.refrigerator.controller.response.StorageDesignResponse;
import com.dragontrain.md.domain.refrigerator.domain.StorageStorageDesign;
import com.dragontrain.md.domain.refrigerator.service.dto.AppliedStorageDesign;
import com.dragontrain.md.domain.refrigerator.service.port.StorageStorageDesignRepository;

import lombok.RequiredArgsConstructor;

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

	@Override
	public List<StorageStorageDesign> findAllSSDByRefrigeratorIdAndSDIds(Long refrigeratorId, List<Integer> designId) {
		return storageStorageDesignJpaRepository.findAllStorageStorageDesignByRefrigeratorIdAndDesignIds(refrigeratorId,
			designId);
	}

	@Override
	public List<StorageStorageDesign> findAllSSDByRefrigeratorIdAndIdApplied(Long refrigeratorId, Boolean isApplied) {
		return storageStorageDesignJpaRepository.findAllByRefrigerator_RefrigeratorIdAndIsApplied(refrigeratorId,
			isApplied);
	}

	@Override
	public void save(StorageStorageDesign storageStorageDesign) {
		storageStorageDesignJpaRepository.save(storageStorageDesign);
	}
}
