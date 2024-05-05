package com.dragontrain.md.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dragontrain.md.common.config.exception.GlobalErrorCode;
import com.dragontrain.md.common.config.exception.TokenException;
import com.dragontrain.md.common.config.jwt.JwtProvider;
import com.dragontrain.md.domain.user.domain.User;
import com.dragontrain.md.domain.user.exception.UserErrorCode;
import com.dragontrain.md.domain.user.exception.UserException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final JwtProvider jwtProvider;

	@Override
	public User loadUserByUserId(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(
				() -> new UserException(UserErrorCode.USER_RESOURCE_NOT_FOUND, "userid " + userId + " is not found"));
	}

	@Override
	public Tokens reissueToken(String refreshTokenCookieValue) {

		if (!jwtProvider.isRefreshToken(refreshTokenCookieValue)) {
			throw new TokenException(GlobalErrorCode.TOKEN_TYPE_MISS_MATCHED);
		}

		Long userId = jwtProvider.parseUserId(refreshTokenCookieValue);

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserException(UserErrorCode.USER_RESOURCE_NOT_FOUND));

		if (user.isDeleted()) {
			throw new UserException(UserErrorCode.ACCESS_DELETED_USER);
		}
		return Tokens.of(jwtProvider.createAccessToken(userId), jwtProvider.createRefreshToken(userId));
	}
}
