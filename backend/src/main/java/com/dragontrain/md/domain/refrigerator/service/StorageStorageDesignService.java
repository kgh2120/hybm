package com.dragontrain.md.domain.refrigerator.service;

import com.dragontrain.md.domain.refrigerator.controller.request.ModifyAppliedStorageDesignRequest;
import com.dragontrain.md.domain.refrigerator.controller.response.AppliedStorageDesignsResponse;
import com.dragontrain.md.domain.refrigerator.controller.response.StorageDesignsResponse;
import com.dragontrain.md.domain.user.domain.User;

public interface StorageStorageDesignService {
	StorageDesignsResponse findAllStorageDesign(User user);

	AppliedStorageDesignsResponse findAllAppliedStorageDesign(User user);

	void modifyAppliedStorageDesign(User user, ModifyAppliedStorageDesignRequest request);

	void acquireNewStorageDesign(Long userId, Integer originalLevel, Integer afterLevel);
}
