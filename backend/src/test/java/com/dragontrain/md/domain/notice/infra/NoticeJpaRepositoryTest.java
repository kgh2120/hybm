package com.dragontrain.md.domain.notice.infra;

import com.dragontrain.md.domain.TestEntityFactory;
import com.dragontrain.md.domain.food.infra.CategoryBigJpaRepository;
import com.dragontrain.md.domain.food.infra.CategoryDetailJpaRepository;
import com.dragontrain.md.domain.food.infra.FoodJpaRepository;
import com.dragontrain.md.domain.refrigerator.infra.LevelJpaRepository;
import com.dragontrain.md.domain.refrigerator.infra.RefrigeratorJpaRepository;
import com.dragontrain.md.domain.user.infra.UserJpaRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class NoticeJpaRepositoryTest {

	@Autowired
	private NoticeJpaRepository noticeJpaRepository;

	@Autowired
	private CategoryBigJpaRepository categoryBigJpaRepository;

	@Autowired
	private CategoryDetailJpaRepository categoryDetailJpaRepository;

	@Autowired
	private FoodJpaRepository foodJpaRepository;

	@Autowired
	private LevelJpaRepository levelJpaRepository;

	@Autowired
	private RefrigeratorJpaRepository refrigeratorJpaRepository;

	@Autowired
	private UserJpaRepository userJpaRepository;

	private static TestEntityFactory testEntityFactory;

	@BeforeAll
	static void 장전(){
		testEntityFactory = new TestEntityFactory();
	}

	@Test
	void 알림전체조회_성공(){

	}

}
