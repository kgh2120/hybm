package com.dragontrain.md.domain.refrigerator.infra;

import com.dragontrain.md.domain.TestEntityFactory;
import com.dragontrain.md.domain.refrigerator.domain.Level;
import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;
import com.dragontrain.md.domain.user.domain.User;
import com.dragontrain.md.domain.user.infra.JpaUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class RefrigeratorJpaRepositoryTest {
	@Autowired
	private RefrigeratorJpaRepository refrigeratorJpaRepository;

	@Autowired
	private JpaUserRepository jpaUserRepository;

	@Autowired
	private LevelJpaRepository levelJpaRepository;

	private static TestEntityFactory testEntityFactory;

	@BeforeAll
	static void 장전(){
		testEntityFactory = new TestEntityFactory();
	}

	@Test
	void 유저아이디로_냉장고조회_성공(){
		User user = testEntityFactory.getTestUserEntity(null);
		jpaUserRepository.save(user);

		Level level = testEntityFactory.getTestLevelEntity(null,1, 1);
		levelJpaRepository.save(level);

		Refrigerator refrigerator = testEntityFactory.getTestRefrigerator(null, user, Boolean.FALSE, level);
		refrigeratorJpaRepository.save(refrigerator);

		Assertions.assertDoesNotThrow(() -> refrigeratorJpaRepository.findByUserId(user.getUserId()));
	}
}
