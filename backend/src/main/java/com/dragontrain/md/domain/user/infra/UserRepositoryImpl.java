package com.dragontrain.md.domain.user.infra;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.dragontrain.md.domain.user.domain.SocialLoginType;
import com.dragontrain.md.domain.user.domain.User;
import com.dragontrain.md.domain.user.service.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

	private final UserJpaRepository jpaUserRepository;

	@Override
	public User save(User user) {
		return jpaUserRepository.save(user);
	}

	@Override
	public Optional<User> findByEmailAndSocialLoginType(String email, SocialLoginType socialLoginType) {
		return jpaUserRepository.findByEmailAndSocialLoginTypeAndIsDeletedFalse(email, socialLoginType);
	}

	@Override
	public Optional<User> findById(Long userId) {
		return jpaUserRepository.findByUserIdAndIsDeletedFalse(userId);
	}
}
