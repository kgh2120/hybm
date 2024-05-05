package com.dragontrain.md.domain.refrigerator.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dragontrain.md.domain.refrigerator.controller.response.MyLevelResponse;
import com.dragontrain.md.domain.refrigerator.domain.Level;
import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorErrorCode;
import com.dragontrain.md.domain.refrigerator.exception.RefrigeratorException;
import com.dragontrain.md.domain.refrigerator.service.port.RefrigeratorRepository;
import com.dragontrain.md.domain.user.domain.User;

@ExtendWith(MockitoExtension.class)
class LevelServiceImplTest {

	@Mock
	RefrigeratorRepository refrigeratorRepository;

	@InjectMocks
	LevelServiceImpl levelService;

	@DisplayName("내 레벨 조회 테스트 성공")
	@Test
	void getMyLevelSuccessTest() throws Exception{
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
	void getMyLevelFailTest() throws Exception{
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

}
