package com.dragontrain.md.domain.food.infra;

import com.dragontrain.md.domain.TestEntityFactory;
import com.dragontrain.md.domain.refrigerator.domain.Level;
import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;
import com.dragontrain.md.domain.refrigerator.infra.LevelJpaRepository;
import com.dragontrain.md.domain.refrigerator.infra.RefrigeratorJpaRepository;
import com.dragontrain.md.domain.user.domain.User;
import com.dragontrain.md.domain.user.infra.UserJpaRepository;
import com.dragontrain.md.domain.user.service.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryBigJpaRepositoryTest {

	@Autowired
	private UserJpaRepository userJpaRepository;

	@Autowired
	private LevelJpaRepository levelJpaRepository;

	@Autowired
	private RefrigeratorJpaRepository refrigeratorJpaRepository;

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


	}

}
