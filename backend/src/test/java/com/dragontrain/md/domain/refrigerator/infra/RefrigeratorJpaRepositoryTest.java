package com.dragontrain.md.domain.refrigerator.infra;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.dragontrain.md.domain.TestEntityFactory;
import com.dragontrain.md.domain.refrigerator.domain.Level;
import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;
import com.dragontrain.md.domain.user.domain.User;
import com.dragontrain.md.domain.user.infra.UserJpaRepository;

@ActiveProfiles("test")
@DataJpaTest
class RefrigeratorJpaRepositoryTest {
	@Autowired
	private RefrigeratorJpaRepository refrigeratorJpaRepository;

	@Autowired
	private UserJpaRepository jpaUserRepository;

	@Autowired
	private LevelJpaRepository levelJpaRepository;

	private static TestEntityFactory testEntityFactory;

	@BeforeAll
	static void 장전() {
		testEntityFactory = new TestEntityFactory();
	}

	@Test
	void 유저아이디로_냉장고조회_성공() {
		User user = testEntityFactory.getTestUserEntity();
		jpaUserRepository.save(user);

		Level level = testEntityFactory.getTestLevelEntity(1, 1);
		levelJpaRepository.save(level);

		Refrigerator refrigerator = testEntityFactory.getTestRefrigerator(user, Boolean.FALSE, level);
		refrigeratorJpaRepository.save(refrigerator);

		Assertions.assertDoesNotThrow(() -> refrigeratorJpaRepository.findByUser_UserId(user.getUserId()));
	}
}
