package com.dragontrain.md.domain.refrigerator.service.port;

import com.dragontrain.md.domain.refrigerator.controller.response.AppliedStorageDesign;
import com.dragontrain.md.domain.refrigerator.controller.response.StorageDesignResponse;
import com.dragontrain.md.domain.refrigerator.domain.StorageStorageDesign;

import java.util.List;

public interface StorageStorageDesignRepository {
	List<StorageDesignResponse> findAllStorageDesign(Long refrigeratorId);
	List<AppliedStorageDesign> findAllAppliedStorageDesign(Long refrigeratorId);

	List<StorageStorageDesign> findAllSSDByRefrigeratorIdAndSDIds(Long refrigeratorId, List<Integer> designId);

	List<StorageStorageDesign> findAllSSDByRefrigeratorIdAndIdApplied(Long refrigeratorId, Boolean isApplied);
	void save(StorageStorageDesign storageStorageDesign);
}
