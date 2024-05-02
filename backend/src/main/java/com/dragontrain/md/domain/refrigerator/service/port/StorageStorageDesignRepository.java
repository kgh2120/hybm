package com.dragontrain.md.domain.refrigerator.service.port;

import java.util.List;

import com.dragontrain.md.domain.refrigerator.controller.response.AppliedStorageDesign;
import com.dragontrain.md.domain.refrigerator.controller.response.StorageDesignResponse;
import com.dragontrain.md.domain.refrigerator.domain.StorageStorageDesign;

public interface StorageStorageDesignRepository {
	List<StorageDesignResponse> findAllStorageDesign(Long refrigeratorId);

	List<AppliedStorageDesign> findAllAppliedStorageDesign(Long refrigeratorId);

	void save(StorageStorageDesign storageStorageDesign);
}
