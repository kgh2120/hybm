package com.dragontrain.md.domain.refrigerator.infra;

import com.dragontrain.md.domain.TestEntityFactory;
import com.dragontrain.md.domain.refrigerator.controller.Response.AppliedStorageDesign;
import com.dragontrain.md.domain.refrigerator.controller.Response.StorageDesignResponse;
import com.dragontrain.md.domain.refrigerator.domain.*;
import com.dragontrain.md.domain.user.domain.User;
import com.dragontrain.md.domain.user.infra.JpaUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class StorageDesignJpaRepositoryTest {
	@Autowired
	private JpaUserRepository jpaUserRepository;

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

	@BeforeAll
	static void 장전(){
		testEntityFactory = new TestEntityFactory();
	}

	@Test
	void 냉장고디자인전체조회_성공() throws Exception{

		User user = testEntityFactory.getTestUserEntity(null);
		jpaUserRepository.save(user);

		Level level = testEntityFactory.getTestLevelEntity(null, 1, 1);
		levelJpaRepository.save(level);
		storageTypeJpaRepository.saveAll(testEntityFactory.getAllTestStorageTypes());
		StorageType cool = storageTypeJpaRepository.findById(StorageTypeId.COOL)
			.orElseThrow(() -> new Exception());

		Refrigerator refrigerator = testEntityFactory.getTestRefrigerator(null, user, Boolean.FALSE, level);
		refrigeratorJpaRepository.save(refrigerator);

		StorageDesign storageDesignCoolMine = testEntityFactory.getTestMineNotUseDesign(null, cool);
		StorageDesign storageDesignCoolNotMine = testEntityFactory.getTestNotMineStorageDesign(null, cool);
		StorageDesign storageDesignCoolMineApplied = testEntityFactory.getTestMineUseDesign(null, cool);
		storageDesignJpaRepository.save(storageDesignCoolMine);
		storageDesignJpaRepository.save(storageDesignCoolNotMine);
		storageDesignJpaRepository.save(storageDesignCoolMineApplied);

		StorageStorageDesign storageStorageDesignApplied = testEntityFactory.getTestStorageStorageDesignApplied(cool, storageDesignCoolMineApplied, refrigerator);
		StorageStorageDesign storageStorageDesignNotApplied = testEntityFactory.getTestStorageStorageDesignNotApplied(cool, storageDesignCoolMine, refrigerator);
		storageStorageDesignJpaRepository.save(storageStorageDesignApplied);
		storageStorageDesignJpaRepository.save(storageStorageDesignNotApplied);

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
		User user = testEntityFactory.getTestUserEntity(null);
		jpaUserRepository.save(user);

		Level level = testEntityFactory.getTestLevelEntity(null, 1, 1);
		levelJpaRepository.save(level);
		storageTypeJpaRepository.saveAll(testEntityFactory.getAllTestStorageTypes());
		StorageType cool = storageTypeJpaRepository.findById(StorageTypeId.COOL)
				.orElseThrow(() -> new Exception());

		Refrigerator refrigerator = testEntityFactory.getTestRefrigerator(null, user, Boolean.FALSE, level);
		refrigeratorJpaRepository.save(refrigerator);

		StorageDesign storageDesignCoolMine = testEntityFactory.getTestMineNotUseDesign(null, cool);
		StorageDesign storageDesignCoolNotMine = testEntityFactory.getTestNotMineStorageDesign(null, cool);
		StorageDesign storageDesignCoolMineApplied = testEntityFactory.getTestMineUseDesign(null, cool);
		storageDesignJpaRepository.save(storageDesignCoolMine);
		storageDesignJpaRepository.save(storageDesignCoolNotMine);
		storageDesignJpaRepository.save(storageDesignCoolMineApplied);

		StorageStorageDesign storageStorageDesignApplied = testEntityFactory.getTestStorageStorageDesignApplied(cool, storageDesignCoolMineApplied, refrigerator);
		StorageStorageDesign storageStorageDesignNotApplied = testEntityFactory.getTestStorageStorageDesignNotApplied(cool, storageDesignCoolMine, refrigerator);
		storageStorageDesignJpaRepository.save(storageStorageDesignApplied);
		storageStorageDesignJpaRepository.save(storageStorageDesignNotApplied);

		Assertions.assertEquals(levelJpaRepository.findAll().size(), 1);
		Assertions.assertEquals(storageTypeJpaRepository.findAll().size(), 3);
		Assertions.assertEquals(refrigeratorJpaRepository.findAll().size(), 1);
		Assertions.assertEquals(storageDesignJpaRepository.findAll().size(), 3);
		Assertions.assertEquals(storageStorageDesignJpaRepository.findAll().size(), 2);

		Assertions.assertEquals(storageStorageDesignJpaRepository.findAllAppliedStorageDesign(1L).size(), 1);
	}
}
