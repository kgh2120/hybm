package com.dragontrain.md.domain.user.oauth;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.dragontrain.md.domain.user.domain.SocialLoginType;

import lombok.Getter;

@Getter
public abstract class AbstractCustomOAuth2Client implements OAuth2User {

	private SocialLoginType socialLoginType;
	private String userEmail;
	private Long userId;
	private OAuth2User originOAuth2User;

	public AbstractCustomOAuth2Client(SocialLoginType socialLoginType, String userEmail, OAuth2User originOAuth2User) {
		this.socialLoginType = socialLoginType;
		this.userEmail = userEmail;
		this.originOAuth2User = originOAuth2User;
	}

	@Override
	public <A> A getAttribute(String name) {
		return originOAuth2User.getAttribute(name);
	}

	@Override
	public Map<String, Object> getAttributes() {
		return originOAuth2User.getAttributes();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return originOAuth2User.getAuthorities();
	}

	@Override
	public String getName() {
		return originOAuth2User.getName();
	}

	public void registerUserId(Long userId) {
		this.userId = userId;
	}
}
