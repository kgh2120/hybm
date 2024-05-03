package com.dragontrain.md.domain.user.controller;

import static com.dragontrain.md.common.config.exception.GlobalErrorCode.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
class LoginIntegrationTest {


	@Autowired
	MockMvc mockMvc;

	@WithMockUser
	@DisplayName("로그인 상태 체크 테스트 성공")
	@Test
	void isLoginSuccessTest() throws Exception {
		// given
		final String path = "/api/users/is-login";
		// when // then
		mockMvc.perform(get(path))
			.andExpectAll(
				status().isOk()
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
	void logoutTest() throws Exception{
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
