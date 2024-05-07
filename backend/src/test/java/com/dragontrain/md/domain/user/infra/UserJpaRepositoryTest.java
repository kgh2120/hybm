package com.dragontrain.md.domain.user.infra;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.dragontrain.md.domain.user.domain.SocialLoginType;
import com.dragontrain.md.domain.user.domain.User;
import com.dragontrain.md.setting.JpaTest;

import jakarta.persistence.EntityManager;

class UserJpaRepositoryTest extends JpaTest {

	@Autowired
	UserJpaRepository userJpaRepository;
	@Autowired
	EntityManager em;

	User activeUser;
	User deletedUser;
	@BeforeEach
	void beforeEach(){
		activeUser = User.create("active@naver.com", SocialLoginType.NAVER, LocalDateTime.of(2024, 5, 6, 13, 23));
		deletedUser = User.create("delete@naver.com", SocialLoginType.NAVER, LocalDateTime.of(2024, 5, 6, 13, 23));
		deletedUser.delete(LocalDateTime.of(2024,5,6,14,20));

		userJpaRepository.save(activeUser);
		userJpaRepository.save(deletedUser);
		em.flush();
		em.clear();
	}

	@AfterEach
	void afterEach(){
		userJpaRepository.deleteAll();
		em.flush();
		em.clear();
	}

	@DisplayName("아이디로 회원 찾기 - 액티브 유저인 경우")
	@Test
	void findByIdActiveUserTest() throws Exception{

	    //when
		Optional<User> sut = userJpaRepository.findByUserIdAndIsDeletedFalse(
			activeUser.getUserId());
		//then
		SoftAssertions sa = new SoftAssertions();
		sa.assertThat(sut.isPresent()).isTrue();
		User user = sut.get();
		sa.assertThat(user.getEmail()).isEqualTo("active@naver.com");
		sa.assertThat(user.getSocialLoginType()).isEqualTo(SocialLoginType.NAVER);
		sa.assertAll();
	}

	@DisplayName("아이디로 회원 찾기 - 삭제된 유저인 경우")
	@Test
	void findByIdDeletedUserTest() throws Exception{

		//when
		Optional<User> sut = userJpaRepository.findByUserIdAndIsDeletedFalse(
			deletedUser.getUserId());
		//then
		SoftAssertions sa = new SoftAssertions();
		sa.assertThat(sut.isEmpty()).isTrue();
		sa.assertAll();
	}

	@DisplayName("이메일, 소셜 로그인 타입으로 유저 찾기 - 액티브 유저인 경우")
	@Test
	void findByEmailAndTypeActiveUserTest() throws Exception{

		//when
		Optional<User> sut = userJpaRepository.findByEmailAndSocialLoginTypeAndIsDeletedFalse("active@naver.com", SocialLoginType.NAVER);
		//then
		SoftAssertions sa = new SoftAssertions();
		sa.assertThat(sut.isPresent()).isTrue();
		User user = sut.get();
		sa.assertThat(user.getEmail()).isEqualTo("active@naver.com");
		sa.assertThat(user.getSocialLoginType()).isEqualTo(SocialLoginType.NAVER);
		sa.assertAll();
	}

	@DisplayName("아이디로 회원 찾기 - 삭제된 유저인 경우")
	@Test
	void findByEmailAndTypeDeletedUserTest() throws Exception{

		//when
		Optional<User> sut = userJpaRepository.findByEmailAndSocialLoginTypeAndIsDeletedFalse("delete@naver.com", SocialLoginType.NAVER);
		//then
		SoftAssertions sa = new SoftAssertions();
		sa.assertThat(sut.isEmpty()).isTrue();
		sa.assertAll();
	}

}
