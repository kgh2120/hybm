package com.dragontrain.md.domain.user.oauth;

import java.io.Serializable;

import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.util.ObjectUtils;

import com.dragontrain.md.domain.user.exception.UserErrorCode;
import com.dragontrain.md.domain.user.exception.UserException;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public final class CustomOAuth2AuthorizationRequest implements Serializable {

	private final OAuth2AuthorizationRequest oAuth2AuthorizationRequest;
	private final String frontendRedirectUri;

	private CustomOAuth2AuthorizationRequest() {
		throw new IllegalArgumentException();
	}

	public static CustomOAuth2AuthorizationRequest of(OAuth2AuthorizationRequest oAuth2AuthorizationRequest,
		String frontendRedirectUri) {
		return new CustomOAuth2AuthorizationRequest(oAuth2AuthorizationRequest, frontendRedirectUri);
	}

	public void validateInnerField() {
		if (ObjectUtils.isEmpty(oAuth2AuthorizationRequest)) {
			throw new UserException(UserErrorCode.OAUTH2_EXCEPTION,
				"CustomOAuth2AuthorizationRequest's oAuth2AuthorizationRequest is null");
		}
		if (ObjectUtils.isEmpty(frontendRedirectUri)) {
			throw new UserException(UserErrorCode.OAUTH2_REDIRECT_URL_MISSING,
				"CustomOAuth2AuthorizationRequest's frontendRedirectUri is null");
		}
	}
}

