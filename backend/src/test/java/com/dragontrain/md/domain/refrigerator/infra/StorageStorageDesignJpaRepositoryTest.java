package com.dragontrain.md.domain.refrigerator.infra;

import com.dragontrain.md.domain.TestEntityFactory;
import com.dragontrain.md.domain.refrigerator.controller.response.AppliedStorageDesign;
import com.dragontrain.md.domain.refrigerator.controller.response.StorageDesignResponse;
import com.dragontrain.md.domain.refrigerator.domain.*;
import com.dragontrain.md.domain.user.domain.User;
import com.dragontrain.md.domain.user.infra.UserJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@DataJpaTest
class StorageStorageDesignJpaRepositoryTest {
	@Autowired
	private UserJpaRepository userJpaRepository;

	@Autowired
	private StorageDesignJpaRepository storageDesignJpaRepository;

	@Autowired
	private StorageStorageDesignJpaRepository storageStorageDesignJpaRepository;

	@Autowired
	private RefrigeratorJpaRepository refrigeratorJpaRepository;

	@Autowired
	private StorageTypeJpaRepository storageTypeJpaRepository;

	@Autowired
	private LevelJpaRepository levelJpaRepository;

	private static TestEntityFactory testEntityFactory;

	private User user;
	private Level level;
	private StorageType cool;
	private Refrigerator refrigerator;
	private StorageDesign storageDesignCoolMine;
	private StorageDesign storageDesignCoolNotMine;
	private StorageDesign storageDesignCoolMineApplied;
	private Integer sdMineId, sdNotMineId, sdMineAppliedId;
	private StorageStorageDesign storageStorageDesignApplied;
	private StorageStorageDesign storageStorageDesignNotApplied;

	@BeforeEach
	void 장전(){
		testEntityFactory = new TestEntityFactory();

		user = testEntityFactory.getTestUserEntity(null);
		userJpaRepository.save(user);

		if(levelJpaRepository.findAll().size() == 0) {
			level = testEntityFactory.getTestLevelEntity(null, 1, 1);
			levelJpaRepository.save(level);
		} else {
			level = levelJpaRepository.findByLevel(1).get();
		}

		if(storageTypeJpaRepository.findAll().size() == 0) {
			storageTypeJpaRepository.saveAll(testEntityFactory.getAllTestStorageTypes());
		}
		cool = storageTypeJpaRepository.findById(StorageTypeId.COOL).get();

		refrigerator = testEntityFactory.getTestRefrigerator(null, user, Boolean.FALSE, level);
		refrigeratorJpaRepository.save(refrigerator);

		if(storageDesignJpaRepository.findAll().size() == 0) {
			storageDesignCoolMine = testEntityFactory.getTestMineNotUseDesign(null, cool);
			storageDesignCoolNotMine = testEntityFactory.getTestNotMineStorageDesign(null, cool);
			storageDesignCoolMineApplied = testEntityFactory.getTestMineUseDesign(null, cool);
			storageDesignJpaRepository.save(storageDesignCoolMine);
			storageDesignJpaRepository.save(storageDesignCoolNotMine);
			storageDesignJpaRepository.save(storageDesignCoolMineApplied);
			sdMineId = storageDesignCoolMine.getStorageDesignId();
			sdNotMineId = storageDesignCoolNotMine.getStorageDesignId();
			sdMineAppliedId = storageDesignCoolMineApplied.getStorageDesignId();
		} else {
			storageDesignCoolMine = storageDesignJpaRepository.findByStorageDesignId(sdMineId).get();
			storageDesignCoolNotMine = storageDesignJpaRepository.findByStorageDesignId(sdNotMineId).get();
			storageDesignCoolMineApplied = storageDesignJpaRepository.findByStorageDesignId(sdMineAppliedId).get();
		}


			storageStorageDesignApplied = testEntityFactory.getTestStorageStorageDesignApplied(cool, storageDesignCoolMineApplied, refrigerator);
			storageStorageDesignNotApplied = testEntityFactory.getTestStorageStorageDesignNotApplied(cool, storageDesignCoolMine, refrigerator);
			storageStorageDesignJpaRepository.save(storageStorageDesignApplied);
			storageStorageDesignJpaRepository.save(storageStorageDesignNotApplied);
	}

	@Test
	void 냉장고디자인전체조회_성공() throws Exception{

		Assertions.assertEquals(levelJpaRepository.findAll().size(), 1);
		Assertions.assertEquals(storageTypeJpaRepository.findAll().size(), 3);
		Assertions.assertEquals(refrigeratorJpaRepository.findAll().size(), 1);
		Assertions.assertEquals(storageDesignJpaRepository.findAll().size(), 3);
		Assertions.assertEquals(storageStorageDesignJpaRepository.findAll().size(), 2);

		Assertions.assertEquals(storageStorageDesignJpaRepository.findAllStorageDesign(refrigerator.getRefrigeratorId()).size(), 3);

		Map<Boolean, List<StorageDesignResponse>> mineNotMine = storageStorageDesignJpaRepository.findAllStorageDesign(refrigerator.getRefrigeratorId())
			.stream()
			.collect(Collectors.groupingBy(StorageDesignResponse::getHas));

		Assertions.assertEquals(mineNotMine.get(true).size(), 2);
		Assertions.assertEquals(mineNotMine.get(false).size(), 1);
	}

	@Test
	void 현재적용디자인조회_성공() throws Exception{

		Assertions.assertEquals(levelJpaRepository.findAll().size(), 1);
		Assertions.assertEquals(storageTypeJpaRepository.findAll().size(), 3);
		Assertions.assertEquals(refrigeratorJpaRepository.findAll().size(), 1);
		Assertions.assertEquals(storageDesignJpaRepository.findAll().size(), 3);
		Assertions.assertEquals(storageStorageDesignJpaRepository.findAll().size(), 2);

		Assertions.assertEquals(storageStorageDesignJpaRepository.findAllAppliedStorageDesign(refrigerator.getRefrigeratorId()).size(), 1);
	}

	@Test
	void 냉장고_적용디자인_확인_성공() throws Exception{
		List<StorageStorageDesign> appliedStorageStorageDesigns = storageStorageDesignJpaRepository.findAllByRefrigerator_RefrigeratorIdAndIsApplied(
			refrigerator.getRefrigeratorId(), Boolean.TRUE
		);

		List<StorageStorageDesign> notAppliedstorageStorageDesigns = storageStorageDesignJpaRepository.findAllByRefrigerator_RefrigeratorIdAndIsApplied(
			refrigerator.getRefrigeratorId(), Boolean.FALSE
		);

		Assertions.assertEquals(appliedStorageStorageDesigns.size(), 1);
		Assertions.assertEquals(notAppliedstorageStorageDesigns.size(), 1);
	}

	@Test
	void 냉장고와_디자인에_따라_저장고저장고디자인찾기_성공(){
		List<StorageStorageDesign> storageStorageDesigns = storageStorageDesignJpaRepository.findAllStorageStorageDesignByRefrigeratorIdAndDesignIds(
			refrigerator.getRefrigeratorId(), Arrays.asList(sdMineId, sdMineAppliedId));

		Assertions.assertEquals(storageStorageDesigns.size(), 2);
	}

	@Test
	void 냉장고_적용여부에_따라_저장고저장고디자인찾기_성공(){
		List<StorageStorageDesign> appliedStorageStorageDesigns = storageStorageDesignJpaRepository.findAllByRefrigerator_RefrigeratorIdAndIsApplied(
			refrigerator.getRefrigeratorId(), Boolean.TRUE);
		Assertions.assertEquals(appliedStorageStorageDesigns.size(), 1);

		List<StorageStorageDesign> notAppliedStorageStorageDesigns = storageStorageDesignJpaRepository.findAllByRefrigerator_RefrigeratorIdAndIsApplied(
			refrigerator.getRefrigeratorId(), Boolean.FALSE);
		Assertions.assertEquals(notAppliedStorageStorageDesigns.size(), 1);
	}

}
