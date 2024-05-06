package com.dragontrain.md.domain.refrigerator.controller;

import static com.dragontrain.md.domain.refrigerator.exception.RefrigeratorErrorCode.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.dragontrain.md.domain.refrigerator.controller.response.MyLevelResponse;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorException;
import com.dragontrain.md.domain.refrigerator.service.LevelService;

@WebMvcTest(controllers = LevelController.class)
class LevelControllerTest {

	@MockBean
	LevelService levelService;

	@InjectMocks
	LevelController levelController;

	@Autowired
	MockMvc mockMvc;


	@WithMockUser
	@DisplayName("내 레벨/경험치 조회 테스트 성공")
	@Test
	void getMyLevelSuccessTest() throws Exception{
	    //given
		final String path = "/api/levels";
		given(levelService.getMyLevel(any()))
			.willReturn(MyLevelResponse.of(1,50,35));
	    //when //then
		mockMvc.perform(get(path))
			.andExpectAll(
				status().isOk(),
				jsonPath("$.level").value(1),
				jsonPath("$.maxExp").value(50),
				jsonPath("$.currentExp").value(35)
			);
	}

	@WithMockUser
	@DisplayName("내 레벨/경험치 조회 테스트 실패 - 아이디로 검색되는 냉장고가 없는 경우")
	@Test
	void getMyLevelFailRefrigeratorMissingTest() throws Exception{
		//given
		final String path = "/api/levels";
		given(levelService.getMyLevel(any()))
			.willThrow(new RefrigeratorException(REFRIGERATOR_NOT_FOUND));
		//when //then
		mockMvc.perform(get(path))
			.andExpectAll(
				status().isNotFound(),
				jsonPath("$.errorName").value(REFRIGERATOR_NOT_FOUND.getErrorName()),
				jsonPath("$.errorMessage").value(REFRIGERATOR_NOT_FOUND.getErrorMessage())
			);
	}

}
