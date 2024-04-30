package com.dragontrain.md.domain.refrigerator.infra;

import com.dragontrain.md.domain.refrigerator.controller.Response.StorageDesignResponse;
import com.dragontrain.md.domain.refrigerator.service.port.StorageDesignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class StorageDesignRepositoryImpl implements StorageDesignRepository {
	private final StorageDesignJpaRepository storageDesignJpaRepository;
}
