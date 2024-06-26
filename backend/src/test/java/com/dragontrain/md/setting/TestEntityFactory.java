package com.dragontrain.md.setting;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.dragontrain.md.domain.food.domain.CategoryBig;
import com.dragontrain.md.domain.food.domain.CategoryDetail;
import com.dragontrain.md.domain.food.domain.Food;
import com.dragontrain.md.domain.food.domain.FoodDeleteType;
import com.dragontrain.md.domain.food.domain.FoodStatus;
import com.dragontrain.md.domain.notice.domain.Notice;
import com.dragontrain.md.domain.notice.domain.NoticeType;
import com.dragontrain.md.domain.refrigerator.service.dto.AppliedStorageDesign;
import com.dragontrain.md.domain.refrigerator.controller.response.StorageDesignResponse;
import com.dragontrain.md.domain.refrigerator.controller.response.StorageDesignsResponse;
import com.dragontrain.md.domain.refrigerator.domain.Level;
import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;
import com.dragontrain.md.domain.refrigerator.domain.StorageDesign;
import com.dragontrain.md.domain.refrigerator.domain.StorageStorageDesign;
import com.dragontrain.md.domain.refrigerator.domain.StorageStorageDesignId;
import com.dragontrain.md.domain.refrigerator.domain.StorageType;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;
import com.dragontrain.md.domain.user.domain.SocialLoginType;
import com.dragontrain.md.domain.user.domain.User;

public class TestEntityFactory {
	public User getTestUserEntity() {
		return User.builder()
			.email("ssafy@ssafy.com")
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.isDeleted(Boolean.FALSE)
			.socialLoginType(SocialLoginType.NAVER)
			.build();
	}

	public User getTestUserEntity(Long id) {
		return User.builder()
			.userId(id)
			.email("ssafy@ssafy.com")
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.isDeleted(Boolean.FALSE)
			.socialLoginType(SocialLoginType.NAVER)
			.build();
	}

	public Level getTestLevelEntity(int level, int maxExp) {
		return Level.builder()
			.level(level)
			.maxExp(maxExp)
			.build();
	}

	public Level getTestLevelEntity(Integer id, int level, int maxExp) {
		return Level.builder()
			.levelId(id)
			.level(level)
			.maxExp(maxExp)
			.build();
	}

	public Refrigerator getTestRefrigerator(User user, Boolean isDeleted, Level level) {
		return Refrigerator.builder()
			.level(level)
			.exp(0)
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.user(user)
			.isDeleted(isDeleted)
			.build();
	}

	public Refrigerator getTestRefrigerator(Long id, User user, Boolean isDeleted, Level level) {
		return Refrigerator.builder()
			.refrigeratorId(id)
			.level(level)
			.exp(0)
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.user(user)
			.isDeleted(isDeleted)
			.build();
	}

	public List<StorageType> getAllTestStorageTypes() {
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

	public StorageDesign getTestNotMineStorageDesign(StorageType storageType) {
		return StorageDesign.builder()
			.storageDesignName("내거아님")
			.level(5)
			.imgSrc("내거아님이미지")
			.storageType(storageType)
			.build();
	}

	public StorageDesign getTestMineNotUseDesign(StorageType storageType) {
		return StorageDesign.builder()
			.storageDesignName("내건데안씀")
			.level(5)
			.imgSrc("내건데안씀이미지")
			.storageType(storageType)
			.build();
	}

	public StorageDesign getTestMineUseDesign(StorageType storageType) {
		return StorageDesign.builder()
			.storageDesignName("내건데안씀")
			.level(5)
			.imgSrc("내건데안씀이미지")
			.storageType(storageType)
			.build();
	}

	public StorageDesign getTestMineUseDesign(Integer id, StorageType storageType) {
		return StorageDesign.builder()
			.storageDesignId(id)
			.storageDesignName("내건데안씀")
			.level(5)
			.imgSrc("내건데안씀이미지")
			.storageType(storageType)
			.build();
	}

	public StorageDesignResponse getTestMyStorageDesignResponse(Integer level, String type) {
		return StorageDesignResponse.builder()
			.name("내테스트디자인레스폰스")
			.has(Boolean.TRUE)
			.designImgSrc("내테스트디자인")
			.isApplied(Boolean.FALSE)
			.location(type)
			.level(level)
			.build();
	}

	public StorageStorageDesign getTestStorageStorageDesignNotApplied(StorageType storageType,
		StorageDesign storageDesign, Refrigerator refrigerator) {
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

	public StorageStorageDesign getTestStorageStorageDesignApplied(StorageType storageType, StorageDesign storageDesign,
		Refrigerator refrigerator) {
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

	public StorageDesignsResponse getTestStorageDesignsResponse(List<StorageDesignResponse> responses) {
		return StorageDesignsResponse.createByStorageType(responses);
	}

	public AppliedStorageDesign getTestAppliedStorageDesign(Integer id, String imgSrc, StorageTypeId type) {
		return AppliedStorageDesign.builder()
			.id(id)
			.imgSrc(imgSrc)
			.type(type)
			.build();
	}

	public CategoryBig getCategoryBig(String name, String imgSrc) {
		return CategoryBig.builder()
			.name(name)
			.imgSrc(imgSrc)
			.build();
	}

	public CategoryDetail getCategoryDetail(String name, String imgSrc, CategoryBig categoryBig) {
		return CategoryDetail.builder()
			.name(name)
			.imgSrc(imgSrc)
			.categoryBig(categoryBig)
			.expirationDate(10)
			.build();
	}

	public Food getFood(String name, Integer price, Refrigerator refrigerator, StorageType type,
		CategoryDetail categoryDetail
		, LocalDateTime createdAt, LocalDateTime updatedAt) {
		return Food.builder()
			.name(name)
			.price(price)
			.refrigerator(refrigerator)
			.storageType(type)
			.categoryDetail(categoryDetail)
			.foodStatus(FoodStatus.FRESH)
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.isManual(Boolean.FALSE)
			.expectedExpirationDate(LocalDate.now())
			.build();
	}

	public Food getDeletedFood(String name, Integer price, Refrigerator refrigerator, StorageType type,
		CategoryDetail categoryDetail
		, LocalDateTime createdAt, LocalDateTime updatedAt, FoodDeleteType foodDeleteType, LocalDateTime deletedAt) {
		return Food.builder()
			.name(name)
			.price(price)
			.refrigerator(refrigerator)
			.storageType(type)
			.categoryDetail(categoryDetail)
			.foodStatus(FoodStatus.FRESH)
			.foodDeleteType(foodDeleteType)
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.deletedAt(deletedAt)
			.isManual(Boolean.FALSE)
			.expectedExpirationDate(LocalDate.now())
			.build();
	}

	public Notice getNotice(String content, Boolean isChecked,
							NoticeType noticeType, Food food){
		return Notice.builder()
			.content(content)
			.isChecked(isChecked)
			.type(noticeType)
			.food(food)
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();
	}

	public Notice getDeletedNotice(String content, Boolean isChecked,
								   NoticeType noticeType, Food food){
		return Notice.builder()
			.content(content)
			.isChecked(isChecked)
			.type(noticeType)
			.food(food)
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.deletedAt(LocalDateTime.now())
			.build();
	}
}
