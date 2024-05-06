package com.dragontrain.md.domain.user.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.dragontrain.md.common.config.event.Events;
import com.dragontrain.md.common.service.EventPublisher;
import com.dragontrain.md.common.service.TimeService;
import com.dragontrain.md.domain.user.domain.SocialLoginType;
import com.dragontrain.md.domain.user.domain.User;
import com.dragontrain.md.domain.user.event.UserCreated;
import com.dragontrain.md.domain.user.service.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2Service extends DefaultOAuth2UserService {

	private final UserRepository userRepository;
	private final TimeService timeService;
	private final EventPublisher eventPublisher;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		AbstractCustomOAuth2Client oAuth2Client = CustomOAuth2ClientFactory.createCustomOAuth2Client(
			userRequest.getClientRegistration().getRegistrationId(), super.loadUser(userRequest));

		User user = userRepository.findByEmailAndSocialLoginType(oAuth2Client.getUserEmail(),
				oAuth2Client.getSocialLoginType())
			.orElseGet(() -> saveUser(oAuth2Client.getUserEmail(), oAuth2Client.getSocialLoginType())
			);

		oAuth2Client.registerUserId(user.getUserId());
		return oAuth2Client;
	}

	public User saveUser(String email, SocialLoginType socialLoginType) {
		User user = userRepository.save(User.create(email, socialLoginType, timeService.localDateTimeNow()));
		eventPublisher.publish(new UserCreated(user.getUserId()));
		return user;
	}
}
