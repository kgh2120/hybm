package com.dragontrain.md.common.config.jwt;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Token {

	private final String value;
	private final long ttl;

	public static Token of(String value, long ttl) {
		return new Token(value, ttl);
	}

	public int getTtlToSecond() {
		return (int)(ttl / 1000);
	}
}
