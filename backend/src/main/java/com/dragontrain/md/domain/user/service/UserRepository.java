package com.dragontrain.md.domain.user.service;

import java.util.Optional;

import com.dragontrain.md.domain.user.domain.SocialLoginType;
import com.dragontrain.md.domain.user.domain.User;

public interface UserRepository {

	User save(User user);

	Optional<User> findByEmailAndSocialLoginType(String email, SocialLoginType socialLoginType);

	Optional<User> findById(Long userId);
}
