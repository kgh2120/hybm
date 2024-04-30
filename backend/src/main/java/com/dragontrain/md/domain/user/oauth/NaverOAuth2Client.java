package com.dragontrain.md.domain.user.oauth;

import org.springframework.security.oauth2.core.user.OAuth2User;

import com.dragontrain.md.domain.user.domain.SocialLoginType;

public class NaverOAuth2Client extends AbstractCustomOAuth2Client {

	public NaverOAuth2Client(String userEmail, OAuth2User originOAuth2User) {
		super(SocialLoginType.NAVER, userEmail, originOAuth2User);
	}
}
