package com.dragontrain.md.domain.user.oauth;

import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import jakarta.servlet.http.HttpServletRequest;

public interface CustomAuthorizationRequestRepository
	extends AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

	String getRedirectUri(HttpServletRequest request);

	void removeCustomOAuth2AuthorizationRequest(HttpServletRequest request);

	CustomOAuth2AuthorizationRequest loadCustomOAuth2AuthorizationRequest(HttpServletRequest request);
}
