package com.dragontrain.md.domain.user.controller;

import static com.dragontrain.md.common.config.exception.GlobalErrorCode.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import com.dragontrain.md.domain.user.domain.SocialLoginType;
import com.dragontrain.md.domain.user.domain.User;

@SpringBootTest
@AutoConfigureMockMvc
class LoginIntegrationTest {

	@Autowired
	MockMvc mockMvc;


	@DisplayName("로그인 상태 체크 테스트 성공")
	@Test
	void isLoginSuccessTest() throws Exception {
		// given
		final String path = "/api/users/is-login";

		LocalDateTime dateTime = LocalDateTime.of(2024, 5, 8, 9, 40);
		User user = User.builder()
			.userId(1L)
			.email("tester@naver.com")
			.socialLoginType(SocialLoginType.NAVER)
			.isDeleted(false)
			.createdAt(dateTime)
			.updatedAt(dateTime)
			.build();
		SecurityContextHolder.getContext()
			.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getUserId(), new ArrayList<>()));

		// when // then
		mockMvc.perform(get(path))
			.andExpectAll(
				status().isOk(),
				jsonPath("$.year").value(2024),
				jsonPath("$.month").value(5)
			).andDo(print());
	}

	@DisplayName("로그인 상태 체크 테스트 실패")
	@Test
	void isLoginFailTest() throws Exception {
		// given
		final String path = "/api/users/is-login";
		// when // then
		mockMvc.perform(get(path))
			.andExpectAll(
				status().isFound(),
				redirectedUrlPattern("**/api/users/login-fail")
			).andDo(print());
	}

	@Test
	void loginFailTest() throws Exception {
		// given
		final String path = "/api/users/login-fail";
		// when// then
		mockMvc.perform(get(path))
			.andExpectAll(
				status().isUnauthorized(),
				jsonPath("$.errorMessage").value(UNAUTHORIZED_ACCESS.getErrorMessage()),
				jsonPath("$.errorName").value(UNAUTHORIZED_ACCESS.getErrorName())
			).andDo(print());
	}

	@WithMockUser
	@DisplayName("로그아웃 테스트 - 만료 쿠기 2개가 리턴된다.")
	@Test
	void logoutTest() throws Exception {
		// given
		final String path = "/api/users/logout";
		// when
		mockMvc.perform(get(path))
			.andExpectAll(
				status().isOk(),
				cookie().exists("access_token"),
				cookie().exists("refresh_token")
			).andDo(print());

		// then
	}

}
