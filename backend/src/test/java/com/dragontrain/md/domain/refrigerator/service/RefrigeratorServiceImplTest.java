package com.dragontrain.md.domain.refrigerator.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import com.dragontrain.md.domain.food.domain.CategoryBig;
import com.dragontrain.md.domain.food.domain.CategoryDetail;
import com.dragontrain.md.domain.food.service.port.CategoryBigRepository;
import com.dragontrain.md.domain.food.service.port.CategoryDetailRepository;
import com.dragontrain.md.domain.refrigerator.domain.*;
import com.dragontrain.md.domain.refrigerator.service.port.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dragontrain.md.common.service.TimeService;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorErrorCode;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorException;
import com.dragontrain.md.domain.user.domain.SocialLoginType;
import com.dragontrain.md.domain.user.domain.User;
import com.dragontrain.md.domain.user.exception.UserErrorCode;
import com.dragontrain.md.domain.user.exception.UserException;
import com.dragontrain.md.domain.user.service.UserRepository;

@ExtendWith(MockitoExtension.class)
class RefrigeratorServiceImplTest {

	@Mock
	LevelRepository levelRepository;
	@Mock
	UserRepository userRepository;

	@Mock
	RefrigeratorRepository refrigeratorRepository;
	@Mock
	StorageDesignRepository storageDesignRepository;
	@Mock
	StorageStorageDesignRepository storageStorageDesignRepository;
	@Mock
	BadgeRepository badgeRepository;
	@Mock
	RefrigeratorBadgeRepository refrigeratorBadgeRepository;
	@Mock
	CategoryBigRepository categoryBigRepository;
	@Mock
	CategoryDetailRepository categoryDetailRepository;
	@Mock
	TimeService timeService;

	@InjectMocks
	RefrigeratorServiceImpl refrigeratorService;

	@DisplayName("냉장고 초기 설정 테스트 성공")
	@Test
	void refrigeratorInitSuccessTest() throws Exception {
		// given
		Long userId = 1L;
		String email = "email";
		SocialLoginType socialLoginType = SocialLoginType.NAVER;
		LocalDateTime now = LocalDateTime.of(2024, 5, 1, 17, 42);
		given(timeService.localDateTimeNow())
			.willReturn(now);
		User givenUser = User.builder()
			.userId(userId)
			.email(email)
			.socialLoginType(socialLoginType)
			.createdAt(now)
			.updatedAt(now)
			.isDeleted(false)
			.build();
		given(userRepository.findById(any()))
			.willReturn(Optional.of(givenUser));
		Level givenLevel = Level.builder()
			.levelId(1)
			.level(1)
			.maxExp(50)
			.build();
		given(levelRepository.findLevel(anyInt()))
			.willReturn(Optional.of(givenLevel));
		for (StorageTypeId storageTypeId : StorageTypeId.values()) {
			given(storageDesignRepository.findStorageDesignByLevelAndType(eq(1), eq(storageTypeId)))
				.willReturn(Optional.of(StorageDesign.builder()
					.level(1)
					.storageType(StorageType.builder()
						.storageType(storageTypeId)
						.build())
					.build()));
		}
		// when
		refrigeratorService.createInitialRefrigerator(userId);
		// then

		then(refrigeratorRepository).should().save(any());
		then(storageStorageDesignRepository).should(atLeast(3)).save(any());
		then(storageDesignRepository).should(atLeast(3)).findStorageDesignByLevelAndType(anyInt(), any());
	}

	@DisplayName("냉장고 초기 설정 테스트 실패 - 유저 아이디가 존재하지 않는 경우")
	@Test
	void refrigeratorInitFailUserIdNotFoundTest() throws Exception {
		// given
		Long userId = 1L;
		given(userRepository.findById(any()))
			.willReturn(Optional.empty());

		// when
		// then
		assertThatThrownBy(() -> refrigeratorService.createInitialRefrigerator(userId))
			.isInstanceOf(UserException.class)
			.hasFieldOrPropertyWithValue("errorCode", UserErrorCode.USER_RESOURCE_NOT_FOUND);
	}

	@DisplayName("냉장고 초기 설정 테스트 실패 - 저장고 디자인을 찾을 수 없는 경우")
	@Test
	void refrigeratorInitFailStorageDesignNotFoundTest() throws Exception {
		// given
		Long userId = 1L;
		String email = "email";
		SocialLoginType socialLoginType = SocialLoginType.NAVER;
		LocalDateTime now = LocalDateTime.of(2024, 5, 1, 17, 42);
		given(timeService.localDateTimeNow())
			.willReturn(now);
		User givenUser = User.builder()
			.userId(userId)
			.email(email)
			.socialLoginType(socialLoginType)
			.createdAt(now)
			.updatedAt(now)
			.isDeleted(false)
			.build();
		given(userRepository.findById(any()))
			.willReturn(Optional.of(givenUser));
		Level givenLevel = Level.builder()
			.levelId(1)
			.level(1)
			.maxExp(50)
			.build();
		given(levelRepository.findLevel(anyInt()))
			.willReturn(Optional.of(givenLevel));

		given(storageDesignRepository.findStorageDesignByLevelAndType(eq(1), any()))
			.willReturn(Optional.empty());

		// when 		// then
		assertThatThrownBy(() -> refrigeratorService.createInitialRefrigerator(userId))
			.isInstanceOf(RefrigeratorException.class)
			.hasFieldOrPropertyWithValue("errorCode", RefrigeratorErrorCode.STORAGE_DESIGN_RESOURCE_NOT_FOUND);

	}

	@DisplayName("냉장고 초기 설정 테스트 실패 - 레벨을 찾을 수 없는 경우")
	@Test
	void refrigeratorInitFailLevelNotFoundTest() throws Exception {
		// given
		Long userId = 1L;
		String email = "email";
		SocialLoginType socialLoginType = SocialLoginType.NAVER;
		LocalDateTime now = LocalDateTime.of(2024, 5, 1, 17, 42);
		User givenUser = User.builder()
			.userId(userId)
			.email(email)
			.socialLoginType(socialLoginType)
			.createdAt(now)
			.updatedAt(now)
			.isDeleted(false)
			.build();
		given(userRepository.findById(any()))
			.willReturn(Optional.of(givenUser));
		given(levelRepository.findLevel(anyInt()))
			.willReturn(Optional.empty());

		// when // then
		assertThatThrownBy(() -> refrigeratorService.createInitialRefrigerator(userId))
			.isInstanceOf(RefrigeratorException.class)
			.hasFieldOrPropertyWithValue("errorCode", RefrigeratorErrorCode.LEVEL_RESOURCE_NOT_FOUND);

	}

	@DisplayName("냉장고 삭제 테스트 성공")
	@Test
	void deleteRefrigeratorSuccessTest() throws Exception{
	    //given
		final Long userId = 1L;
		Refrigerator refrigerator = Refrigerator.builder()
			.isDeleted(false)
			.build();
		given(refrigeratorRepository.findByUserId(anyLong()))
			.willReturn(Optional.of(refrigerator));
		given(timeService.localDateTimeNow())
			.willReturn(LocalDateTime.of(2024,5,6,13,14));
	    //when //then
		refrigeratorService.deleteRefrigerator(userId);
	}

	@DisplayName("냉장고 삭제 테스트 실패 - 냉장고가 없는 경우")
	@Test
	void deleteRefrigeratorFailRefrigeratorNotFoundedTest() throws Exception{
		//given
		final Long userId = 1L;

		given(refrigeratorRepository.findByUserId(anyLong()))
			.willReturn(Optional.empty());

		//when //then
		assertThatThrownBy(() -> refrigeratorService.deleteRefrigerator(userId))
			.isInstanceOf(RefrigeratorException.class)
			.hasFieldOrPropertyWithValue("errorCode", RefrigeratorErrorCode.REFRIGERATOR_NOT_FOUND);
	}

	@DisplayName("냉장고 삭제 테스트 실패 - 냉장고가 이미 삭제된 경우")
	@Test
	void deleteRefrigeratorFailRefrigeratorAlreadyDeletedTest() throws Exception{
		//given
		final Long userId = 1L;

		Refrigerator refrigerator = Refrigerator.builder()
			.isDeleted(true)
			.build();
		given(refrigeratorRepository.findByUserId(anyLong()))
			.willReturn(Optional.of(refrigerator));
		given(timeService.localDateTimeNow())
			.willReturn(LocalDateTime.of(2024,5,6,13,14));

		//when //then
		assertThatThrownBy(() -> refrigeratorService.deleteRefrigerator(userId))
			.isInstanceOf(RefrigeratorException.class)
			.hasFieldOrPropertyWithValue("errorCode", RefrigeratorErrorCode.ALREADY_DELETED_REFRIGERATOR);
	}

	@DisplayName("뱃지얻기 성공")
	@Test
	void eatenToGotBadgeSuccessTest() throws Exception{
		//given
		final Long userId = 1L;

		Refrigerator refrigerator = Refrigerator.builder().build();

		CategoryBig categoryBig = CategoryBig.builder()
			.build();

		Badge badge = Badge.builder()
			.build();

		given(badgeRepository.findBadgeByCategoryBigId(categoryBig.getCategoryBigId()))
			.willReturn(Optional.of(badge));

		RefrigeratorBadge refrigeratorBadge = RefrigeratorBadge.builder().build();

		// when
		refrigeratorService.gotBadge(refrigerator.getRefrigeratorId(), categoryBig.getCategoryBigId());

		// then
		then(refrigeratorBadgeRepository).should().save(any());
	}

}
