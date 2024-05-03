package com.dragontrain.md.domain.refrigerator.infra;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dragontrain.md.domain.refrigerator.service.dto.AppliedStorageDesign;
import com.dragontrain.md.domain.refrigerator.controller.response.StorageDesignResponse;
import com.dragontrain.md.domain.refrigerator.domain.StorageStorageDesign;

public interface StorageStorageDesignJpaRepository extends JpaRepository<StorageStorageDesign, Long> {
	@Query("select new com.dragontrain.md.domain.refrigerator.controller.response.StorageDesignResponse" +
		"(sd.storageDesignId, sd.storageDesignName, sd.imgSrc, ssd.isApplied, sd.level, CASE WHEN ssd is null THEN false ELSE true END, sd.storageType.typeName)" +
		" from StorageStorageDesign ssd right join ssd.storageDesign sd" +
		" on ssd.refrigerator.refrigeratorId=:refrigeratorId")
	List<StorageDesignResponse> findAllStorageDesign(Long refrigeratorId);

	@Query("select new com.dragontrain.md.domain.refrigerator.service.dto.AppliedStorageDesign" +
		"(sd.storageDesignId, sd.imgSrc, ssd.storageType.storageType)" +
		" from StorageStorageDesign ssd join ssd.storageDesign sd" +
		" on ssd.refrigerator.refrigeratorId=:refrigeratorId and ssd.isApplied=true")
	List<AppliedStorageDesign> findAllAppliedStorageDesign(Long refrigeratorId);

	@Query("select ssd from StorageStorageDesign ssd" +
		" where ssd.storageStorageDesignId.storageDesignId in :designIds" +
		" and ssd.refrigerator.refrigeratorId=:refrigeratorId")
	List<StorageStorageDesign> findAllStorageStorageDesignByRefrigeratorIdAndDesignIds(Long refrigeratorId, List<Integer> designIds);

	List<StorageStorageDesign> findAllByRefrigerator_RefrigeratorIdAndIsApplied(Long refrigeratorId, Boolean isApplied);
}
