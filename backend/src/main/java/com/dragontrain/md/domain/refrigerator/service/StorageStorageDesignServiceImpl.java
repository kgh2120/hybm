package com.dragontrain.md.domain.refrigerator.service;

import com.dragontrain.md.domain.refrigerator.controller.response.StorageDesignsResponse;
import com.dragontrain.md.domain.refrigerator.service.port.RefrigeratorRepository;
import com.dragontrain.md.domain.refrigerator.service.port.StorageStorageDesignRepository;
import com.dragontrain.md.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StorageStorageDesignServiceImpl implements StorageStorageDesignService{

	private final StorageStorageDesignRepository storageDesignRepository;
	private final RefrigeratorRepository refrigeratorRepository;

	@Override
	public StorageDesignsResponse findAllStorageDesign(User user) {
		return StorageDesignsResponse.createByStorageType(
			storageDesignRepository.findAllStorageDesign(
				refrigeratorRepository.findByUserId(user.getUserId()).getRefrigeratorId()
			)
		);
	}
}
