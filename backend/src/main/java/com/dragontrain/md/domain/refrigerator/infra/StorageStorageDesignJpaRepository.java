package com.dragontrain.md.domain.refrigerator.infra;

import com.dragontrain.md.domain.refrigerator.controller.response.AppliedStorageDesign;
import com.dragontrain.md.domain.refrigerator.controller.response.StorageDesignResponse;
import com.dragontrain.md.domain.refrigerator.domain.StorageDesign;
import com.dragontrain.md.domain.refrigerator.domain.StorageStorageDesign;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StorageStorageDesignJpaRepository extends JpaRepository<StorageStorageDesign, Long> {
	@Query("select new com.dragontrain.md.domain.refrigerator.controller.response.StorageDesignResponse" +
		"(sd.storageDesignId, sd.storageDesignName, sd.imgSrc, ssd.isApplied, sd.level, CASE WHEN ssd is null THEN false ELSE true END, sd.storageType.typeName)" +
		" from StorageStorageDesign ssd right join ssd.storageDesign sd" +
		" on ssd.refrigerator.refrigeratorId=:refrigeratorId")
	List<StorageDesignResponse> findAllStorageDesign(Long refrigeratorId);

	@Query("select new com.dragontrain.md.domain.refrigerator.controller.response.AppliedStorageDesign" +
		"(sd.storageDesignId, sd.imgSrc, ssd.storageType.storageType)" +
		" from StorageStorageDesign ssd join ssd.storageDesign sd" +
		" on ssd.refrigerator.refrigeratorId=:refrigeratorId and ssd.isApplied=true")
	List<AppliedStorageDesign> findAllAppliedStorageDesign(Long refrigeratorId);
}
