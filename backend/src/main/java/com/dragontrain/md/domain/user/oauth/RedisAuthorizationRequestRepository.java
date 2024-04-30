package com.dragontrain.md.domain.user.oauth;

import static com.dragontrain.md.domain.user.exception.UserErrorCode.*;

import java.util.Base64;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.SerializationUtils;
import org.springframework.util.StringUtils;

import com.dragontrain.md.domain.user.exception.UserException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisAuthorizationRequestRepository implements
	CustomAuthorizationRequestRepository {

	private final RedisTemplate<String, String> stringRedisTemplate;

	@Override
	public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
		return loadCustomOAuth2AuthorizationRequest(request).getOAuth2AuthorizationRequest();
	}

	@Override
	public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request,
		HttpServletResponse response) {
		log.info("저장합니다잉~ 얘를 : AuthorizationRequestUri : {}, AuthorizationUri :  {}, getRedirectUri :  {}, ",
			authorizationRequest.getAuthorizationRequestUri(),
			authorizationRequest.getAuthorizationUri(),
			authorizationRequest.getRedirectUri());

		String redirectUri = request.getParameter("redirect_url");
		if (!StringUtils.hasText(redirectUri)) {
			throw new IllegalArgumentException("Redirect URI가 존재하지 않습니다");
		}
		CustomOAuth2AuthorizationRequest customOAuth2AuthorizationRequest = CustomOAuth2AuthorizationRequest.of(
			authorizationRequest, redirectUri);

		ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
		ops.set(authorizationRequest.getState(), Base64.getUrlEncoder()
			.encodeToString(SerializationUtils.serialize(customOAuth2AuthorizationRequest)), 30, TimeUnit.MINUTES);
	}

	@Override
	public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request,
		HttpServletResponse response) {
		return loadAuthorizationRequest(request);
	}

	@Override
	public String getRedirectUri(HttpServletRequest request) {
		return loadCustomOAuth2AuthorizationRequest(request).getFrontendRedirectUri();
	}

	@Override
	public void removeCustomOAuth2AuthorizationRequest(HttpServletRequest request) {
		stringRedisTemplate.delete(request.getParameter("state"));
	}

	@Override
	public CustomOAuth2AuthorizationRequest loadCustomOAuth2AuthorizationRequest(HttpServletRequest request) {
		ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
		byte[] decoded = Base64.getUrlDecoder().decode(ops.get(request.getParameter("state")));

		if (ObjectUtils.isEmpty(decoded)) {
			throw new UserException(MISSING_AUTHORIZATION_REQUEST);
		}

		CustomOAuth2AuthorizationRequest authorizationRequest = (CustomOAuth2AuthorizationRequest)(SerializationUtils.deserialize(
			decoded));
		authorizationRequest.validateInnerField();
		return authorizationRequest;
	}
}
