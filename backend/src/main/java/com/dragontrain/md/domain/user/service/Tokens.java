package com.dragontrain.md.domain.user.service;

import com.dragontrain.md.common.config.jwt.Token;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Tokens {

	private final Token accessToken;
	private final Token refreshToken;

	public static Tokens of(Token accessToken, Token refreshToken) {
		return new Tokens(accessToken, refreshToken);
	}
}
