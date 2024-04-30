package com.dragontrain.md.domain.refrigerator.service;

import com.dragontrain.md.domain.refrigerator.controller.Response.StorageDesignsResponse;
import com.dragontrain.md.domain.refrigerator.service.port.StorageDesignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StorageDesignServiceImpl implements StorageDesignService {
	private final StorageDesignRepository storageDesignRepository;
}
