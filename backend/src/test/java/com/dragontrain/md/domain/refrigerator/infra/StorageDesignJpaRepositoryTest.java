package com.dragontrain.md.domain.refrigerator.infra;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.dragontrain.md.setting.TestEntityFactory;
import com.dragontrain.md.domain.refrigerator.controller.response.StorageDesignResponse;
import com.dragontrain.md.domain.refrigerator.domain.Level;
import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;
import com.dragontrain.md.domain.refrigerator.domain.StorageDesign;
import com.dragontrain.md.domain.refrigerator.domain.StorageStorageDesign;
import com.dragontrain.md.domain.refrigerator.domain.StorageType;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;
import com.dragontrain.md.domain.user.domain.User;
import com.dragontrain.md.domain.user.infra.UserJpaRepository;

@ActiveProfiles("test")
@DataJpaTest
class StorageDesignJpaRepositoryTest {
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

	private TestEntityFactory testEntityFactory = new TestEntityFactory();

	@Test
	void 냉장고디자인전체조회_성공() throws Exception {

		User user = testEntityFactory.getTestUserEntity();
		userJpaRepository.save(user);

		Level level = testEntityFactory.getTestLevelEntity(1, 1);
		levelJpaRepository.save(level);
		storageTypeJpaRepository.saveAll(testEntityFactory.getAllTestStorageTypes());
		StorageType cool = storageTypeJpaRepository.findById(StorageTypeId.COOL).get();

		Refrigerator refrigerator = testEntityFactory.getTestRefrigerator(user, Boolean.FALSE, level);
		refrigeratorJpaRepository.save(refrigerator);

		StorageDesign storageDesignCoolMine = testEntityFactory.getTestMineNotUseDesign(cool);
		StorageDesign storageDesignCoolNotMine = testEntityFactory.getTestNotMineStorageDesign(cool);
		StorageDesign storageDesignCoolMineApplied = testEntityFactory.getTestMineUseDesign(cool);
		storageDesignJpaRepository.save(storageDesignCoolMine);
		storageDesignJpaRepository.save(storageDesignCoolNotMine);
		storageDesignJpaRepository.save(storageDesignCoolMineApplied);

		StorageStorageDesign storageStorageDesignApplied = testEntityFactory.getTestStorageStorageDesignApplied(cool,
			storageDesignCoolMineApplied, refrigerator);
		StorageStorageDesign storageStorageDesignNotApplied = testEntityFactory.getTestStorageStorageDesignApplied(cool,
			storageDesignCoolMine, refrigerator);
		storageStorageDesignJpaRepository.save(storageStorageDesignApplied);
		storageStorageDesignJpaRepository.save(storageStorageDesignNotApplied);

		Assertions.assertEquals(levelJpaRepository.findAll().size(), 1);
		Assertions.assertEquals(storageTypeJpaRepository.findAll().size(), 3);
		Assertions.assertEquals(refrigeratorJpaRepository.findAll().size(), 1);
		Assertions.assertEquals(storageDesignJpaRepository.findAll().size(), 3);
		Assertions.assertEquals(storageStorageDesignJpaRepository.findAll().size(), 2);

		Assertions.assertEquals(
			storageStorageDesignJpaRepository.findAllStorageDesign(refrigerator.getRefrigeratorId()).size(), 3);

		Map<Boolean, List<StorageDesignResponse>> mineNotMine = storageStorageDesignJpaRepository.findAllStorageDesign(
				refrigerator.getRefrigeratorId())
			.stream()
			.collect(Collectors.groupingBy(StorageDesignResponse::getHas));

		Assertions.assertEquals(mineNotMine.get(true).size(), 2);
		Assertions.assertEquals(mineNotMine.get(false).size(), 1);
	}

	@Test
	void 현재적용디자인조회_성공() throws Exception {
		User user = testEntityFactory.getTestUserEntity();
		userJpaRepository.save(user);

		Level level = testEntityFactory.getTestLevelEntity(1, 1);
		levelJpaRepository.save(level);
		storageTypeJpaRepository.saveAll(testEntityFactory.getAllTestStorageTypes());
		StorageType cool = storageTypeJpaRepository.findById(StorageTypeId.COOL)
			.orElseThrow(() -> new Exception());

		Refrigerator refrigerator = testEntityFactory.getTestRefrigerator(user, Boolean.FALSE, level);
		refrigeratorJpaRepository.save(refrigerator);

		StorageDesign storageDesignCoolMine = testEntityFactory.getTestMineNotUseDesign(cool);
		StorageDesign storageDesignCoolNotMine = testEntityFactory.getTestNotMineStorageDesign(cool);
		StorageDesign storageDesignCoolMineApplied = testEntityFactory.getTestMineUseDesign(cool);
		storageDesignJpaRepository.save(storageDesignCoolMine);
		storageDesignJpaRepository.save(storageDesignCoolNotMine);
		storageDesignJpaRepository.save(storageDesignCoolMineApplied);

		StorageStorageDesign storageStorageDesignApplied = testEntityFactory.getTestStorageStorageDesignApplied(cool,
			storageDesignCoolMineApplied, refrigerator);
		StorageStorageDesign storageStorageDesignNotApplied = testEntityFactory.getTestStorageStorageDesignNotApplied(
			cool, storageDesignCoolMine, refrigerator);
		storageStorageDesignJpaRepository.save(storageStorageDesignApplied);
		storageStorageDesignJpaRepository.save(storageStorageDesignNotApplied);

		Assertions.assertEquals(levelJpaRepository.findAll().size(), 1);
		Assertions.assertEquals(storageTypeJpaRepository.findAll().size(), 3);
		Assertions.assertEquals(refrigeratorJpaRepository.findAll().size(), 1);
		Assertions.assertEquals(storageDesignJpaRepository.findAll().size(), 3);
		Assertions.assertEquals(storageStorageDesignJpaRepository.findAll().size(), 2);

		Assertions.assertEquals(
			storageStorageDesignJpaRepository.findAllAppliedStorageDesign(refrigerator.getRefrigeratorId()).size(), 1);
	}
}
