package com.dragontrain.md.domain.food.infra;

import com.dragontrain.md.domain.TestEntityFactory;
import com.dragontrain.md.domain.food.domain.CategoryBig;
import com.dragontrain.md.domain.food.domain.CategoryDetail;
import com.dragontrain.md.domain.food.domain.Food;
import com.dragontrain.md.domain.refrigerator.domain.Level;
import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;
import com.dragontrain.md.domain.refrigerator.domain.StorageType;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;
import com.dragontrain.md.domain.refrigerator.infra.LevelJpaRepository;
import com.dragontrain.md.domain.refrigerator.infra.RefrigeratorJpaRepository;
import com.dragontrain.md.domain.refrigerator.infra.StorageTypeJpaRepository;
import com.dragontrain.md.domain.user.domain.User;
import com.dragontrain.md.domain.user.infra.UserJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DataJpaTest
class CategoryBigJpaRepositoryTest {

	@Autowired
	private UserJpaRepository userJpaRepository;

	@Autowired
	private LevelJpaRepository levelJpaRepository;

	@Autowired
	private RefrigeratorJpaRepository refrigeratorJpaRepository;

	@Autowired
	private StorageTypeJpaRepository storageTypeJpaRepository;

	@Autowired
	private FoodJpaRepository foodJpaRepository;

	@Autowired
	private CategoryBigJpaRepository categoryBigJpaRepository;

	@Autowired
	private CategoryDetailJpaRepository categoryDetailJpaRepository;

	private static TestEntityFactory testEntityFactory;

	@BeforeAll
	static void 장전(){
		testEntityFactory = new TestEntityFactory();
	}

	@Test
	void 대분류별_가격수집_성공(){
		User user = testEntityFactory.getTestUserEntity();
		userJpaRepository.save(user);
		Level level = testEntityFactory.getTestLevelEntity(1, 1);
		levelJpaRepository.save(level);
		Refrigerator refrigerator = testEntityFactory.getTestRefrigerator(user, Boolean.FALSE, level);
		refrigeratorJpaRepository.save(refrigerator);

		User otherUser = testEntityFactory.getTestUserEntity();
		userJpaRepository.save(otherUser);

		Refrigerator otherRefrigerator = testEntityFactory.getTestRefrigerator(otherUser, Boolean.FALSE, level);
		refrigeratorJpaRepository.save(otherRefrigerator);

		StorageType cool = StorageType.builder().storageType(StorageTypeId.COOL).typeName("cool").build();
		storageTypeJpaRepository.save(cool);

		CategoryBig categoryBig1 = testEntityFactory.getCategoryBig("대분류1", "대분류1미지");
		CategoryBig categoryBig2 = testEntityFactory.getCategoryBig("대분류2", "대분류2미지");
		CategoryBig otherBig = testEntityFactory.getCategoryBig("다른유저것입니다", "대분류3미지");

		categoryBigJpaRepository.saveAll(Arrays.asList(categoryBig1, categoryBig2, otherBig));

		CategoryDetail first = testEntityFactory.getCategoryDetail("첫소분류", "첫소분류이미지", categoryBig1);
		CategoryDetail second = testEntityFactory.getCategoryDetail("두소분류", "두소분류이미지", categoryBig2);
		CategoryDetail third = testEntityFactory.getCategoryDetail("세소분류", "세소분류이미지", categoryBig2);
		CategoryDetail otherDetail = testEntityFactory.getCategoryDetail("다른분류", "다른분류이미지", otherBig);
		categoryDetailJpaRepository.saveAll(Arrays.asList(first, second, third, otherDetail));

		List<Food> foods = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			foods.add(testEntityFactory.getFood(Integer.toString(i + 1000), 5000, refrigerator, cool, first, LocalDateTime.now(), LocalDateTime.now()));
		}

		for(int i = 0; i < 5; i++){
			foods.add(testEntityFactory.getFood(Integer.toString(i + 2000), 5000, refrigerator, cool, second, LocalDateTime.now(), LocalDateTime.now()));
		}

		for(int i = 0; i < 3; i++){
			foods.add(testEntityFactory.getFood(Integer.toString(i + 3000), 5000, refrigerator, cool, third, LocalDateTime.now(), LocalDateTime.now()));
		}

		for(int i = 0; i < 10; i++){
			foods.add(testEntityFactory.getFood("다른냉장고", 5000, otherRefrigerator, cool, otherDetail, LocalDateTime.now(), LocalDateTime.now()));
		}

		for(int i = 0; i < 5; i++){
			foods.add(testEntityFactory.getFood("한달전", 9999, refrigerator, cool, third, LocalDateTime.now().minusMonths(1), LocalDateTime.now().minusMonths(1)));
		}

		foodJpaRepository.saveAll(foods);

		Assertions.assertEquals(categoryBigJpaRepository.findAllBigGroupAndSpend(refrigerator.getRefrigeratorId(), LocalDateTime.now().getYear(), LocalDateTime.now().getMonth().getValue()).size(), 18);
		Assertions.assertEquals(categoryBigJpaRepository.findAllBigGroupAndSpend(refrigerator.getRefrigeratorId(), LocalDateTime.now().getYear(), LocalDateTime.now().getMonth().getValue() - 1).size(), 5);
		Assertions.assertEquals(categoryBigJpaRepository.findAllBigGroupAndSpend(refrigerator.getRefrigeratorId(), LocalDateTime.now().getYear(), LocalDateTime.now().getMonth().getValue() - 2).size(), 0);
	}

}
