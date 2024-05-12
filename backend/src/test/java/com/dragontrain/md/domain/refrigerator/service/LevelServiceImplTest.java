package com.dragontrain.md.domain.refrigerator.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dragontrain.md.common.service.EventPublisher;
import com.dragontrain.md.common.service.TimeService;
import com.dragontrain.md.domain.refrigerator.controller.response.MyLevelResponse;
import com.dragontrain.md.domain.refrigerator.domain.Level;
import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;
import com.dragontrain.md.domain.refrigerator.event.LevelUp;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorErrorCode;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorException;
import com.dragontrain.md.domain.refrigerator.service.port.LevelRepository;
import com.dragontrain.md.domain.refrigerator.service.port.RefrigeratorRepository;
import com.dragontrain.md.domain.user.domain.SocialLoginType;
import com.dragontrain.md.domain.user.domain.User;

@ExtendWith(MockitoExtension.class)
class LevelServiceImplTest {

	@Mock
	RefrigeratorRepository refrigeratorRepository;
	@Mock
	LevelRepository levelRepository;
	@Mock
	EventPublisher eventPublisher;
	@Mock
	TimeService timeService;

	@InjectMocks
	LevelServiceImpl levelService;

	@DisplayName("내 레벨 조회 테스트 성공")
	@Test
	void getMyLevelSuccessTest() throws Exception {
		//given

		User givenUser = User.builder()
			.userId(1L)
			.build();

		Refrigerator givenRefrigerator = Refrigerator.builder()
			.level(Level.builder().level(1).maxExp(50).build())
			.exp(35)
			.refrigeratorId(1L)
			.build();
		given(refrigeratorRepository.findByUserId(anyLong()))
			.willReturn(Optional.of(givenRefrigerator));
		//when
		MyLevelResponse sut = levelService.getMyLevel(givenUser);
		//then
		SoftAssertions sa = new SoftAssertions();
		sa.assertThat(sut.getLevel()).isEqualTo(1);
		sa.assertThat(sut.getCurrentExp()).isEqualTo(35);
		sa.assertThat(sut.getMaxExp()).isEqualTo(50);
		sa.assertAll();
	}

	@DisplayName("내 레벨 조회 테스트 실패")
	@Test
	void getMyLevelFailTest() throws Exception {
		//given

		User givenUser = User.builder()
			.userId(1L)
			.build();

		given(refrigeratorRepository.findByUserId(anyLong()))
			.willReturn(Optional.empty());
		//when//then
		assertThatThrownBy(() -> levelService.getMyLevel(givenUser))
			.isInstanceOf(RefrigeratorException.class)
			.hasFieldOrPropertyWithValue("errorCode", RefrigeratorErrorCode.REFRIGERATOR_NOT_FOUND);
	}

	@DisplayName("경험치 획득 성공 - 레벨업은 안하는 경우")
	@Test
	void acquireExpSuccessOnlyExpTest() throws Exception {
		// given
		long userId = 1L;
		int exp = 30;
		LocalDateTime createdAt = LocalDateTime.of(2024, 5, 10, 13, 9);
		LocalDateTime updatedAt = LocalDateTime.of(2024, 5, 10, 13, 15);
		User user = User.create("tester@naver.com",
			SocialLoginType.NAVER, createdAt);

		Level level = Level.builder()
			.level(1)
			.levelId(1)
			.maxExp(50)
			.build();
		Refrigerator refrigerator = Refrigerator.create(user, level, createdAt);

		given(refrigeratorRepository.findByUserId(userId))
			.willReturn(Optional.of(refrigerator));
		given(timeService.localDateTimeNow())
			.willReturn(updatedAt);
		// when
		levelService.acquireExp(userId, exp);
		// then
		SoftAssertions.assertSoftly((sa) -> {
			sa.assertThat(refrigerator.getExp()).isEqualTo(exp);
			sa.assertThat(refrigerator.getLevel().getLevel()).isEqualTo(level.getLevel());
			sa.assertThat(refrigerator.getUpdatedAt()).isEqualTo(updatedAt);
		});
		then(eventPublisher).should(never()).publish(any());
	}

	@DisplayName("경험치 획득 성공 - 레벨업하는 경우")
	@Test
	void acquireExpSuccessTest() throws Exception {
		// given
		long userId = 1L;
		int exp = 30;
		LocalDateTime createdAt = LocalDateTime.of(2024, 5, 10, 13, 9);
		LocalDateTime updatedAt = LocalDateTime.of(2024, 5, 10, 13, 15);
		User user = User.create("tester@naver.com",
			SocialLoginType.NAVER, createdAt);

		Level currentLevel = Level.builder()
			.level(1)
			.levelId(1)
			.maxExp(50)
			.build();
		Level nextlevel = Level.builder()
			.level(2)
			.levelId(2)
			.maxExp(100)
			.build();
		int currentExp = 40;
		Refrigerator refrigerator = Refrigerator.builder()
			.level(currentLevel)
			.exp(currentExp)
			.user(user)
			.createdAt(createdAt)
			.updatedAt(createdAt)
			.build();


		given(refrigeratorRepository.findByUserId(userId))
			.willReturn(Optional.of(refrigerator));
		given(timeService.localDateTimeNow())
			.willReturn(updatedAt);
		given(levelRepository.getNextLevel(currentLevel.getLevel()))
			.willReturn(Optional.of(nextlevel));
		// when
		levelService.acquireExp(userId, exp);
		// then
		SoftAssertions.assertSoftly((sa) -> {
			int calculatedExp = currentExp + exp - currentLevel.getMaxExp();
			sa.assertThat(refrigerator.getExp()).isEqualTo(calculatedExp);
			sa.assertThat(refrigerator.getLevel().getLevel()).isEqualTo(nextlevel.getLevel());
			sa.assertThat(refrigerator.getUpdatedAt()).isEqualTo(updatedAt);
		});
		then(eventPublisher).should(times(1)).publish(new LevelUp(userId, nextlevel.getLevel()));
	}

}
