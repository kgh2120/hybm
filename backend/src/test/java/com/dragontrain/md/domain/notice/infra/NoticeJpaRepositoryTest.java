package com.dragontrain.md.domain.notice.infra;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;


import com.dragontrain.md.domain.food.domain.CategoryBig;
import com.dragontrain.md.domain.food.domain.CategoryDetail;
import com.dragontrain.md.domain.food.domain.Food;
import com.dragontrain.md.domain.food.infra.CategoryBigJpaRepository;
import com.dragontrain.md.domain.food.infra.CategoryDetailJpaRepository;
import com.dragontrain.md.domain.food.infra.FoodJpaRepository;
import com.dragontrain.md.domain.notice.domain.Notice;
import com.dragontrain.md.domain.notice.domain.NoticeType;
import com.dragontrain.md.domain.refrigerator.domain.Level;
import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;
import com.dragontrain.md.domain.refrigerator.domain.StorageType;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;
import com.dragontrain.md.domain.refrigerator.infra.LevelJpaRepository;
import com.dragontrain.md.domain.refrigerator.infra.RefrigeratorJpaRepository;
import com.dragontrain.md.domain.refrigerator.infra.StorageTypeJpaRepository;
import com.dragontrain.md.domain.user.domain.User;
import com.dragontrain.md.domain.user.infra.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;

import com.dragontrain.md.setting.TestEntityFactory;

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

	@Autowired
	private StorageTypeJpaRepository storageTypeJpaRepository;

	private TestEntityFactory testEntityFactory = new TestEntityFactory();

	private User user;
	private Level level;
	private Refrigerator refrigerator;
	private CategoryDetail categoryDetail;
	private CategoryBig categoryBig;
	private StorageType storageType;
	private Food food;

	@Test
	@BeforeEach
	void 장전(){
		user = testEntityFactory.getTestUserEntity();
		userJpaRepository.save(user);

		level = testEntityFactory.getTestLevelEntity(1, 1);
		levelJpaRepository.save(level);

		refrigerator = testEntityFactory.getTestRefrigerator(user, Boolean.FALSE, level);
		refrigeratorJpaRepository.save(refrigerator);

		categoryBig = testEntityFactory.getCategoryBig("대분류" + user.getUserId(), "대분류이미지");
		categoryBigJpaRepository.save(categoryBig);

		categoryDetail = testEntityFactory.getCategoryDetail("소분류" + user.getUserId(), "소분류이미지", categoryBig);
		categoryDetailJpaRepository.save(categoryDetail);

		storageTypeJpaRepository.saveAll(testEntityFactory.getAllTestStorageTypes());
		storageType = storageTypeJpaRepository.findById(StorageTypeId.COOL).get();

		food = testEntityFactory.getFood("음식1", 5000, refrigerator, storageType, categoryDetail, LocalDateTime.now(), LocalDateTime.now());
		foodJpaRepository.save(food);
	}

	@Test
	void 알림전체조회_성공() {
		List<Notice> notices = new ArrayList<>();
		// 읽은거 안읽은거 삭제된거 1대1대1로 섞어넣기
		for (int i = 0; i < 10; i++) {
			notices.add(testEntityFactory.getNotice("알림들", Boolean.FALSE, NoticeType.TO_DANGER, food));
			notices.add(testEntityFactory.getNotice("알림들", Boolean.FALSE, NoticeType.TO_DANGER, food));
			notices.add(testEntityFactory.getDeletedNotice("삭제된알림", Boolean.TRUE, NoticeType.TO_DANGER, food));
		}
		for (int i = 0; i < 10; i++) {
			notices.add(testEntityFactory.getNotice("알림들22", Boolean.FALSE, NoticeType.TO_DANGER, food));
			notices.add(testEntityFactory.getNotice("알림들22", Boolean.FALSE, NoticeType.TO_DANGER, food));
			notices.add(testEntityFactory.getDeletedNotice("삭제된알림22", Boolean.TRUE, NoticeType.TO_DANGER, food));
		}
		noticeJpaRepository.saveAll(notices);

		Slice<Notice> res1 = noticeJpaRepository.findAllNotDeletedNotice(refrigerator.getRefrigeratorId(), PageRequest.of(0, 20));
		Slice<Notice> res2 = noticeJpaRepository.findAllNotDeletedNotice(refrigerator.getRefrigeratorId(), PageRequest.of(0, 35));
		Slice<Notice> res3 = noticeJpaRepository.findAllNotDeletedNotice(refrigerator.getRefrigeratorId(), PageRequest.of(0, 40));

		Assertions.assertEquals(res1.getSize(), 20);
		Assertions.assertEquals(res1.hasNext(), true);
		Assertions.assertEquals(res2.getSize(), 35);
		Assertions.assertEquals(res2.hasNext(), true);
		Assertions.assertEquals(res3.getSize(), 40);
		Assertions.assertEquals(res3.hasNext(), false);

	}

	@Test
	void 미확인알림여부조회_알림있음_성공() {
		List<Notice> notices = new ArrayList<>();
		notices.add(testEntityFactory.getNotice("알림들", Boolean.FALSE, NoticeType.TO_DANGER, food));
		notices.add(testEntityFactory.getDeletedNotice("삭제된알림", Boolean.TRUE, NoticeType.TO_DANGER, food));
		noticeJpaRepository.saveAll(notices);

		Assertions.assertEquals(noticeJpaRepository.existsByFood_Refrigerator_RefrigeratorIdAndDeletedAtIsNullAndIsCheckedIsFalse(refrigerator.getRefrigeratorId()), Boolean.TRUE);
	}

	@Test
	void 미확인알림여부조회_알림없음_성공(){
		noticeJpaRepository.save(testEntityFactory.getNotice("삭제된알림", Boolean.TRUE, NoticeType.TO_DANGER, food));
		noticeJpaRepository.save(testEntityFactory.getDeletedNotice("삭제된알림", Boolean.TRUE, NoticeType.TO_DANGER, food));
		Assertions.assertEquals(noticeJpaRepository.existsByFood_Refrigerator_RefrigeratorIdAndDeletedAtIsNullAndIsCheckedIsFalse(refrigerator.getRefrigeratorId()), Boolean.FALSE);
	}

	@Test
	void 내_알림없음_성공(){
		User otherUser = testEntityFactory.getTestUserEntity();
		userJpaRepository.save(otherUser);
		Refrigerator otherRef = testEntityFactory.getTestRefrigerator(otherUser, Boolean.FALSE, level);
		refrigeratorJpaRepository.save(otherRef);
		Food otherFood = testEntityFactory.getFood("다른사람음식", 9999, otherRef, storageType, categoryDetail, LocalDateTime.now(), LocalDateTime.now());
		foodJpaRepository.save(otherFood);
		noticeJpaRepository.save(noticeJpaRepository.save(testEntityFactory.getNotice("미확인알림", Boolean.FALSE, NoticeType.TO_DANGER, otherFood)));
		noticeJpaRepository.save(noticeJpaRepository.save(testEntityFactory.getNotice("확인알림", Boolean.TRUE, NoticeType.TO_DANGER, otherFood)));
		Assertions.assertEquals(noticeJpaRepository.existsByFood_Refrigerator_RefrigeratorIdAndDeletedAtIsNullAndIsCheckedIsFalse(refrigerator.getRefrigeratorId()), Boolean.FALSE);
	}

}
