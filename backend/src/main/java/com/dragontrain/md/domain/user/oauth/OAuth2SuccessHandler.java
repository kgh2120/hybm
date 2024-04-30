package com.dragontrain.md.domain.user.oauth;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final CustomAuthorizationRequestRepository customAuthorizationRequestRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		AbstractCustomOAuth2Client oAuth2Client = (AbstractCustomOAuth2Client)authentication.getPrincipal();
		log.debug("userId : {} is entered. SocialType : {}, Email : {}", oAuth2Client.getUserId(), oAuth2Client.getSocialLoginType().name(),
			oAuth2Client.getUserEmail());
		// TODO JWT 설정
		String redirectUri = customAuthorizationRequestRepository.getRedirectUri(request);
		customAuthorizationRequestRepository.removeCustomOAuth2AuthorizationRequest(request);
		getRedirectStrategy().sendRedirect(request, response, redirectUri);
	}
}
