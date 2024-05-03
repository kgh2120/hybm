package com.dragontrain.md.domain.user.controller;

import static com.dragontrain.md.common.config.exception.GlobalErrorCode.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.dragontrain.md.common.config.exception.TokenException;
import com.dragontrain.md.common.config.jwt.Token;
import com.dragontrain.md.common.config.utils.CookieUtils;
import com.dragontrain.md.domain.user.service.Tokens;
import com.dragontrain.md.domain.user.service.UserService;

import jakarta.servlet.http.Cookie;

@WebMvcTest(controllers = {UserController.class})
class UserControllerTest {

	@MockBean
	UserService userService;
	@InjectMocks
	UserController userController;

	@Autowired
	MockMvc mockMvc;

	@WithMockUser
	@DisplayName("토큰 재발행 테스트 성공")
	@Test
	void reissueSuccessTest() throws Exception {
		// given
		final String path = "/api/users/reissue";
		Cookie mockRefreshToken = CookieUtils.makeRefreshTokenCookie("mock", 100000000);
		given(userService.reissueToken(any()))
			.willReturn(Tokens.of(Token.of("mock", 1010111), Token.of("mock", 4949384)));
		// when // then
		mockMvc.perform(post(path)
				.with(csrf())
				.cookie(mockRefreshToken))
			.andExpectAll(
				status().isOk(),
				cookie().exists("access_token"),
				cookie().exists("refresh_token")
			).andDo(print());
	}

	@WithMockUser
	@DisplayName("토큰 재발행 테스트 실패 - 쿠키를 넘겨주지 않은 경우")
	@Test
	void reissueFailRefreshCookieMissingTest() throws Exception {
		// given
		final String path = "/api/users/reissue";

		// when // then
		mockMvc.perform(post(path)
				.with(csrf()))
			.andExpectAll(
				status().isUnauthorized(),
				jsonPath("$.errorName").value(REFRESH_TOKEN_MISSING.getErrorName()),
				jsonPath("$.errorMessage").value(REFRESH_TOKEN_MISSING.getErrorMessage())
			).andDo(print());
	}


	@WithMockUser
	@DisplayName("토큰 재발행 테스트 실패 - 쿠키가 리프래시 토큰이 아닌 경우")
	@Test
	void reissueFailCookieIsNotReissueTest() throws Exception {
		// given
		final String path = "/api/users/reissue";
		Cookie mockRefreshToken = CookieUtils.makeRefreshTokenCookie("mock", 100000000);
		given(userService.reissueToken(any()))
			.willThrow(new TokenException(TOKEN_TYPE_MISS_MATCHED));

		// when // then
		mockMvc.perform(post(path)
				.with(csrf())
				.cookie(mockRefreshToken)
			)
			.andExpectAll(
				status().isForbidden(),
				jsonPath("$.errorName").value(TOKEN_TYPE_MISS_MATCHED.getErrorName()),
				jsonPath("$.errorMessage").value(TOKEN_TYPE_MISS_MATCHED.getErrorMessage())
			).andDo(print());
	}

}
