package com.dragontrain.md.domain;

import com.dragontrain.md.domain.refrigerator.controller.Response.StorageDesignResponse;
import com.dragontrain.md.domain.refrigerator.controller.Response.StorageDesignsResponse;
import com.dragontrain.md.domain.refrigerator.domain.*;
import com.dragontrain.md.domain.user.domain.SocialLoginType;
import com.dragontrain.md.domain.user.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestEntityFactory {
	public User getTestUserEntity(Long userId){
		return User.builder()
			.userId(userId)
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.isDeleted(Boolean.FALSE)
			.socialLoginType(SocialLoginType.NAVER)
			.build();
	}

	public Level getTestLevelEntity(Integer levelId, int level, int maxExp){
		return Level.builder()
			.levelId(levelId)
			.level(level)
			.maxExp(maxExp)
			.build();
	}

	public Refrigerator getTestRefrigerator(Long refId, User user, Boolean isDeleted, Level level){
		return Refrigerator.builder()
			.refrigeratorId(refId)
			.level(level)
			.exp(0)
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.user(user)
			.isDeleted(isDeleted)
			.build();
	}

	public List<StorageType> getAllTestStorageTypes(){
		List<StorageType> storageTypes = new ArrayList<>();
		storageTypes.add(StorageType.builder()
			.storageType(StorageTypeId.ICE)
			.typeName("냉동고")
			.build());
		storageTypes.add(
			StorageType.builder()
				.storageType(StorageTypeId.COOL)
				.typeName("냉장고")
				.build()
		);
		storageTypes.add(
			StorageType.builder()
				.storageType(StorageTypeId.CABINET)
				.typeName("찬장")
				.build()
		);

		return storageTypes;
	}

	public StorageDesign getTestNotMineStorageDesign(Integer storageDesignId, StorageType storageType){
		return StorageDesign.builder()
			.storageDesignId(storageDesignId)
			.storageDesignName("내거아님")
			.level(5)
			.imgSrc("내거아님이미지")
			.storageType(storageType)
			.build();
	}

	public StorageDesign getTestMineNotUseDesign(Integer storageDesignId, StorageType storageType){
		return StorageDesign.builder()
			.storageDesignId(storageDesignId)
			.storageDesignName("내건데안씀")
			.level(5)
			.imgSrc("내건데안씀이미지")
			.storageType(storageType)
			.build();
	}

	public StorageDesign getTestMineUseDesign(Integer storageDesignId, StorageType storageType){
		return StorageDesign.builder()
			.storageDesignId(storageDesignId)
			.storageDesignName("내건데안씀")
			.level(5)
			.imgSrc("내건데안씀이미지")
			.storageType(storageType)
			.build();
	}

	public StorageDesignResponse getTestMyStorageDesignResponse(Integer id, Integer level, StorageTypeId type){
		return StorageDesignResponse.builder()
			.storageDesignId(id)
			.name("내테스트디자인레스폰스")
			.has(Boolean.TRUE)
			.designImgSrc("내테스트디자인")
			.isApplied(Boolean.FALSE)
			.location(type)
			.level(level)
			.build();
	}

	public StorageStorageDesign getTestStorageStorageDesignNotApplied(StorageType storageType, StorageDesign storageDesign, Refrigerator refrigerator){
		return StorageStorageDesign.builder()
			.storageStorageDesignId(StorageStorageDesignId.builder()
				.storageDesignId(storageDesign.getStorageDesignId()).refrigeratorId(refrigerator.getRefrigeratorId())
				.build())
			.isApplied(Boolean.FALSE)
			.createdAt(LocalDateTime.now())
			.storageType(storageType)
			.storageDesign(storageDesign)
			.refrigerator(refrigerator)
			.build();
	}

	public StorageStorageDesign getTestStorageStorageDesignApplied(StorageType storageType, StorageDesign storageDesign, Refrigerator refrigerator){
		return StorageStorageDesign.builder()
			.storageStorageDesignId(StorageStorageDesignId.builder()
				.storageDesignId(storageDesign.getStorageDesignId()).refrigeratorId(refrigerator.getRefrigeratorId())
				.build())
			.isApplied(Boolean.TRUE)
			.createdAt(LocalDateTime.now())
			.storageType(storageType)
			.storageDesign(storageDesign)
			.refrigerator(refrigerator)
			.build();
	}

	public StorageDesignsResponse getTestStorageDesignsResponse(List<StorageDesignResponse> responses){
		return StorageDesignsResponse.createByStorageType(responses);
	}
}
