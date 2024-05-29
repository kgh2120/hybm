package com.dragontrain.md.domain.user.oauth;

import org.springframework.security.oauth2.core.user.OAuth2User;

import com.dragontrain.md.domain.user.domain.SocialLoginType;

public class KakaoOAuth2Client extends AbstractCustomOAuth2Client {

	public KakaoOAuth2Client(String userEmail, OAuth2User originOAuth2User) {
		super(SocialLoginType.KAKAO, userEmail, originOAuth2User);
	}
}
