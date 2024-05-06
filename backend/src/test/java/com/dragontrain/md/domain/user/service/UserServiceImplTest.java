package com.dragontrain.md.domain.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dragontrain.md.common.config.exception.GlobalErrorCode;
import com.dragontrain.md.common.config.exception.TokenException;
import com.dragontrain.md.common.config.jwt.JwtProvider;
import com.dragontrain.md.common.service.EventPublisher;
import com.dragontrain.md.common.service.TimeService;
import com.dragontrain.md.domain.user.domain.User;
import com.dragontrain.md.domain.user.event.UserDeleted;
import com.dragontrain.md.domain.user.exception.UserErrorCode;
import com.dragontrain.md.domain.user.exception.UserException;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

	@Mock
	JwtProvider jwtProvider;

	@Mock
	UserRepository userRepository;
	@Mock EventPublisher eventPublisher;
	@Mock TimeService timeService;

	@InjectMocks
	UserServiceImpl userService;

	@DisplayName("토큰 재발행 테스트 성공")
	@Test
	void reissueTokenSuccessTest() throws Exception {
		// given
		String refreshTokenValue = "hello";
		given(jwtProvider.isRefreshToken(any()))
			.willReturn(true);
		given(jwtProvider.parseUserId(any()))
			.willReturn(1L);

		User user = User.builder()
			.userId(1L)
			.isDeleted(false)
			.build();

		given(userRepository.findById(anyLong()))
			.willReturn(Optional.of(user));
		// when
		userService.reissueToken(refreshTokenValue);
		// then
		then(jwtProvider).should(atLeastOnce()).createAccessToken(anyLong());
		then(jwtProvider).should(atLeastOnce()).createRefreshToken(anyLong());
	}

	@DisplayName("토큰 재발행 테스트 실패 - 토큰이 리프래시 토큰이 아닌 경우")
	@Test
	void reissueTokenFailTokenIsNotRefreshTokenTest() throws Exception {
		// given
		String refreshTokenValue = "hello";
		given(jwtProvider.isRefreshToken(any()))
			.willReturn(false);

		// when
		assertThatThrownBy(() -> userService.reissueToken(refreshTokenValue))
			.isInstanceOf(TokenException.class)
			.hasFieldOrPropertyWithValue("errorCode", GlobalErrorCode.TOKEN_TYPE_MISS_MATCHED);
		// then
		then(userRepository).should(never()).findById(anyLong());
		then(jwtProvider).should(never()).createAccessToken(anyLong());
		then(jwtProvider).should(never()).createRefreshToken(anyLong());
	}

	@DisplayName("토큰 재발행 테스트 실패 - 유저가 존재하지 않는 경우")
	@Test
	void reissueTokenFailUserResourceNotFoundTest() throws Exception {
		// given
		String refreshTokenValue = "hello";
		given(jwtProvider.isRefreshToken(any()))
			.willReturn(true);
		given(jwtProvider.parseUserId(any()))
			.willReturn(1L);

		given(userRepository.findById(anyLong()))
			.willReturn(Optional.empty());
		// when
		assertThatThrownBy(() -> userService.reissueToken(refreshTokenValue))
			.isInstanceOf(UserException.class)
			.hasFieldOrPropertyWithValue("errorCode", UserErrorCode.USER_RESOURCE_NOT_FOUND);
		// then
		then(jwtProvider).should(never()).createAccessToken(anyLong());
		then(jwtProvider).should(never()).createRefreshToken(anyLong());
	}

	@DisplayName("토큰 재발행 테스트 실패 - 유저가 삭제된 경우")
	@Test
	void reissueTokenFailUserAlreadyDeletedTest() throws Exception {
		// given
		String refreshTokenValue = "hello";
		given(jwtProvider.isRefreshToken(any()))
			.willReturn(true);
		given(jwtProvider.parseUserId(any()))
			.willReturn(1L);

		User user = User.builder()
			.userId(1L)
			.isDeleted(true)
			.build();

		given(userRepository.findById(anyLong()))
			.willReturn(Optional.of(user));
		// when
		assertThatThrownBy(() -> userService.reissueToken(refreshTokenValue))
			.isInstanceOf(UserException.class)
			.hasFieldOrPropertyWithValue("errorCode", UserErrorCode.ACCESS_DELETED_USER);
		// then
		then(jwtProvider).should(never()).createAccessToken(anyLong());
		then(jwtProvider).should(never()).createRefreshToken(anyLong());
	}

	@DisplayName("회원 삭제 테스트 성공")
	@Test
	void signOutSuccessTest() throws Exception{
	    //given
		User mockUser = User.builder()
			.isDeleted(false)
			.userId(1L)
			.build();
		given(timeService.localDateTimeNow())
			.willReturn(LocalDateTime.of(2024,5,6,13,0));
		//when
		userService.signOut(mockUser);
	    //then
		then(eventPublisher).should(times(1)).publish(new UserDeleted(mockUser.getUserId()));
	}

	@DisplayName("회원 삭제 테스트 실패")
	@Test
	void signOutFailTest() throws Exception{
		//given
		User mockUser = User.builder()
			.isDeleted(true)
			.userId(1L)
			.build();
		given(timeService.localDateTimeNow())
			.willReturn(LocalDateTime.of(2024,5,6,13,0));
		//when //then
		assertThatThrownBy(() -> userService.signOut(mockUser))
			.isInstanceOf(UserException.class)
			.hasFieldOrPropertyWithValue("errorCode", UserErrorCode.ACCESS_DELETED_USER);
	}

}
