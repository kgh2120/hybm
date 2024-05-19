package com.dragontrain.md.domain.refrigerator.infra;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.dragontrain.md.domain.refrigerator.domain.StorageType;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;
import com.dragontrain.md.domain.refrigerator.service.port.StorageTypeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class StorageTypeRepositoryImpl implements StorageTypeRepository {
	private final StorageTypeJpaRepository storageTypeJpaRepository;

	@Override
	public Optional<StorageType> findById(StorageTypeId id) {
		return storageTypeJpaRepository.findById(id);
	}
}
