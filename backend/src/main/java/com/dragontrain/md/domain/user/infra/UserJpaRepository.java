package com.dragontrain.md.domain.user.infra;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dragontrain.md.domain.user.domain.SocialLoginType;
import com.dragontrain.md.domain.user.domain.User;

public interface UserJpaRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmailAndSocialLoginTypeAndIsDeletedFalse(String email, SocialLoginType socialLoginType);

	Optional<User> findByUserIdAndIsDeletedFalse(Long userId);
}
