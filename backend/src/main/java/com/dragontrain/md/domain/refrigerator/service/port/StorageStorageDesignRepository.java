package com.dragontrain.md.domain.refrigerator.service.port;

import com.dragontrain.md.domain.refrigerator.controller.Response.AppliedStorageDesign;
import com.dragontrain.md.domain.refrigerator.controller.Response.StorageDesignResponse;
import com.dragontrain.md.domain.refrigerator.domain.StorageStorageDesign;

import java.util.List;

public interface StorageStorageDesignRepository {
	List<StorageDesignResponse> findAllStorageDesign(Long refrigeratorId);
	List<AppliedStorageDesign> findAllAppliedStorageDesign(Long refrigeratorId);
}
