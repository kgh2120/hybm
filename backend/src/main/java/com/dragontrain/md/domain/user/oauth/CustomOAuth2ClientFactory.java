package com.dragontrain.md.domain.user.oauth;

import static com.dragontrain.md.domain.user.exception.UserErrorCode.*;

import java.util.Map;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.ObjectUtils;

import com.dragontrain.md.domain.user.exception.UserException;

public class CustomOAuth2ClientFactory {

	private static final String KAKAO_CLIENT_REGISTRATION_ID = "kakao";
	private static final String NAVER_CLIENT_REGISTRATION_ID = "naver";
	private static final String NAVER_USER_ATTRIBUTE_NAME = "response";
	private static final String KAKAO_USER_ATTRIBUTE_NAME = "kakao_account";
	private static final String KAKAO_EMAIL_KEY = "email";
	private static final String NAVER_EMAIL_KEY = "email";

	public static AbstractCustomOAuth2Client createCustomOAuth2Client(String registrationId,
		OAuth2User deglatedOAuth2user) {

		switch (registrationId) {
			case KAKAO_CLIENT_REGISTRATION_ID:
				return new KakaoOAuth2Client(parseKakaoEmailInfo(deglatedOAuth2user), deglatedOAuth2user);
			case NAVER_CLIENT_REGISTRATION_ID:
				return new NaverOAuth2Client(parseNaverEmailInfo(deglatedOAuth2user), deglatedOAuth2user);
			default:
				throw new UserException(OAUTH2_UNKNOWN_REGISTER_ID, "UNKNOWN REGISTER ID IS ENTERED : " + registrationId);
		}
	}

	private static String parseNaverEmailInfo(OAuth2User deglatedOAuth2user) {
		Map<String, Object> naverAccountAttribute = deglatedOAuth2user.getAttribute(NAVER_USER_ATTRIBUTE_NAME);
		Object email = naverAccountAttribute.get(NAVER_EMAIL_KEY);

		if (ObjectUtils.isEmpty(email)) {
			throw new UserException(OAUTH2_EMAIL_DATA_MISSING, "naver's email field is missing");
		}

		return (String)email;
	}

	private static String parseKakaoEmailInfo(OAuth2User deglatedOAuth2user) {
		Map<String, Object> kakaoAccountAttribute = deglatedOAuth2user.getAttribute(KAKAO_USER_ATTRIBUTE_NAME);

		Object email = kakaoAccountAttribute.get(KAKAO_EMAIL_KEY);

		if (ObjectUtils.isEmpty(email)) {
			throw new UserException(OAUTH2_EMAIL_DATA_MISSING, "kakao's email field is missing");
		}
		return (String)email;
	}
}
