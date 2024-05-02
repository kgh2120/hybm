package com.dragontrain.md.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dragontrain.md.domain.user.domain.User;
import com.dragontrain.md.domain.user.exception.UserErrorCode;
import com.dragontrain.md.domain.user.exception.UserException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public User loadUserByUserId(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(
				() -> new UserException(UserErrorCode.USER_RESOURCE_NOT_FOUND, "userid " + userId + " is not found"));
	}
}
