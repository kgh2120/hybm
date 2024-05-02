package com.dragontrain.md.domain.refrigerator.infra;

import com.dragontrain.md.domain.refrigerator.domain.StorageType;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;
import com.dragontrain.md.domain.refrigerator.service.port.StorageTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class StorageTypeRepositoryImpl implements StorageTypeRepository {
	private final StorageTypeJpaRepository storageTypeJpaRepository;

	@Override
	public Optional<StorageType> findById(StorageTypeId id) {
		return storageTypeJpaRepository.findById(id);
	}
}
