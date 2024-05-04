package com.dragontrain.md.domain.food.infra;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.dragontrain.md.domain.TestEntityFactory;
import com.dragontrain.md.domain.food.domain.CategoryBig;
import com.dragontrain.md.domain.food.domain.CategoryDetail;
import com.dragontrain.md.domain.food.domain.FoodDeleteType;
import com.dragontrain.md.domain.refrigerator.domain.Level;
import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;
import com.dragontrain.md.domain.refrigerator.domain.StorageType;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;
import com.dragontrain.md.domain.refrigerator.infra.LevelJpaRepository;
import com.dragontrain.md.domain.refrigerator.infra.RefrigeratorJpaRepository;
import com.dragontrain.md.domain.refrigerator.infra.StorageTypeJpaRepository;
import com.dragontrain.md.domain.user.domain.User;
import com.dragontrain.md.domain.user.infra.UserJpaRepository;

@DataJpaTest
class FoodJpaRepositoryTest {

	@Autowired
	private UserJpaRepository userJpaRepository;

	@Autowired
	private RefrigeratorJpaRepository refrigeratorJpaRepository;

	@Autowired
	private LevelJpaRepository levelJpaRepository;

	@Autowired
	private CategoryBigJpaRepository categoryBigJpaRepository;

	@Autowired
	private CategoryDetailJpaRepository categoryDetailJpaRepository;

	@Autowired
	private FoodJpaRepository foodJpaRepository;

	@Autowired
	private StorageTypeJpaRepository storageTypeJpaRepository;

	private static TestEntityFactory testEntityFactory;

	@BeforeAll
	static void 장전() {
		testEntityFactory = new TestEntityFactory();
	}

	@Test
	void 먹거나_썩어서_삭제한음식획득_성공() {
		User user = testEntityFactory.getTestUserEntity();
		userJpaRepository.save(user);

		Level level = testEntityFactory.getTestLevelEntity(1, 1);
		levelJpaRepository.save(level);

		Refrigerator refrigerator = testEntityFactory.getTestRefrigerator(user, Boolean.FALSE, level);
		refrigeratorJpaRepository.save(refrigerator);

		CategoryBig categoryBig = testEntityFactory.getCategoryBig("임시대분류", "임시대분류이미지");
		categoryBigJpaRepository.save(categoryBig);

		CategoryDetail categoryDetail = testEntityFactory.getCategoryDetail("임시소분류", "임시소분류이미지", categoryBig);
		categoryDetailJpaRepository.save(categoryDetail);

		storageTypeJpaRepository.save(StorageType.builder().storageType(StorageTypeId.COOL).typeName("cool").build());

		for (int i = 0; i < 10; i++) {
			foodJpaRepository.save(testEntityFactory.getDeletedFood("f1", 5000, refrigerator,
				storageTypeJpaRepository.findById(StorageTypeId.COOL).get(), categoryDetail, LocalDateTime.now(),
				LocalDateTime.now(), FoodDeleteType.EATEN, LocalDateTime.now()));
		}

		for (int i = 0; i < 5; i++) {
			foodJpaRepository.save(testEntityFactory.getDeletedFood("f2", 5000, refrigerator,
				storageTypeJpaRepository.findById(StorageTypeId.COOL).get(), categoryDetail, LocalDateTime.now(),
				LocalDateTime.now(), FoodDeleteType.THROWN, LocalDateTime.now()));
		}

		for (int i = 0; i < 10; i++) {
			foodJpaRepository.save(testEntityFactory.getDeletedFood("f2", 5000, refrigerator,
				storageTypeJpaRepository.findById(StorageTypeId.COOL).get(), categoryDetail, LocalDateTime.now(),
				LocalDateTime.now(), FoodDeleteType.CLEAR, LocalDateTime.now()));
		}

		Assertions.assertEquals(foodJpaRepository.findAll().size(), 25);
		Assertions.assertEquals(
			foodJpaRepository.findAllDeletedFoodByRefrigeratorIdAndTime(refrigerator.getRefrigeratorId(),
				LocalDateTime.now().getYear(), LocalDateTime.now().getMonth().getValue()).size(), 15);
	}

}
