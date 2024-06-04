package com.dragontrain.md.domain.user.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dragontrain.md.common.config.exception.GlobalErrorCode;
import com.dragontrain.md.common.config.exception.TokenException;
import com.dragontrain.md.common.config.jwt.JwtProvider;
import com.dragontrain.md.common.service.EventPublisher;
import com.dragontrain.md.common.service.TimeService;
import com.dragontrain.md.domain.user.domain.User;
import com.dragontrain.md.domain.user.event.UserDeleted;
import com.dragontrain.md.domain.user.exception.UserErrorCode;
import com.dragontrain.md.domain.user.exception.UserException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final JwtProvider jwtProvider;
	private final EventPublisher eventPublisher;
	private final TimeService timeService;

	@Cacheable(cacheNames = "user", key = "#userId", cacheManager = "caffeineCacheManager")
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

	@CacheEvict(cacheNames = "user", key = "#user.userId")
	@Transactional
	@Override
	public void signOut(User user) {
		user.delete(timeService.localDateTimeNow());
		userRepository.save(user);
		eventPublisher.publish(new UserDeleted(user.getUserId()));
	}
}
