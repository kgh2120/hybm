package com.dragontrain.md.domain.user.oauth;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Component
public class OAuth2FailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private final CustomAuthorizationRequestRepository customAuthorizationRequestRepository;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException, ServletException {
		log.error("social login authenticationFailure", exception);
		customAuthorizationRequestRepository.removeCustomOAuth2AuthorizationRequest(request);
		getRedirectStrategy().sendRedirect(request, response, getFailureRedirectUrl(request));
	}

	private String getFailureRedirectUrl(HttpServletRequest request) {
		return customAuthorizationRequestRepository.getRedirectUri(request) + "?result=false";
	}
}
