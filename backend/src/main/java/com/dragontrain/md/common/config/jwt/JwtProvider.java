package com.dragontrain.md.common.config.jwt;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider {
	private static final String JWT_CLAIM_KEY_TYPE = "type";
	private static final String JWT_CLAIM_VALUE_ACCESS_TOKEN = "access_token";
	private static final String JWT_CLAIM_VALUE_REFRESH_TOKEN = "refresh_token";

	// 토큰을 만드는 것

	private final JwtProperties jwtProperties;
	private SecretKey secretKey;

	@PostConstruct
	void init() {
		log.info("[JwtProperties]  accessTokenTtl : {}, refreshTokenTtl : {}, secretKey : {}, userIdClaimKey : {}",
			jwtProperties.getAccessTokenTtl(), jwtProperties.getRefreshTokenTtl(), jwtProperties.getSecretKey(),
			jwtProperties.getUserIdClaimKey());
		secretKey = Keys.hmacShaKeyFor(Base64.getEncoder().encode(jwtProperties.getSecretKey().getBytes()));
	}

	public Token createAccessToken(Long userId) {
		Date now = new Date();
		return Token.of(Jwts.builder()
			.claim(jwtProperties.getUserIdClaimKey(), userId)
			.claim(JWT_CLAIM_KEY_TYPE, JWT_CLAIM_VALUE_ACCESS_TOKEN)
			.issuedAt(now)
			.expiration(new Date(now.getTime() + jwtProperties.getAccessTokenTtl()))
			.signWith(secretKey)
			.compact(), jwtProperties.getAccessTokenTtl());
	}

	public Token createRefreshToken(Long userId) {
		Date now = new Date();
		return Token.of(Jwts.builder()
			.claim(jwtProperties.getUserIdClaimKey(), userId)
			.claim(JWT_CLAIM_KEY_TYPE, JWT_CLAIM_VALUE_REFRESH_TOKEN)
			.issuedAt(now)
			.expiration(new Date(now.getTime() + jwtProperties.getRefreshTokenTtl()))
			.signWith(secretKey)
			.compact(), jwtProperties.getAccessTokenTtl());
	}

	public Long parseUserId(String token) throws JwtException {
		return getPayload(token).get(jwtProperties.getUserIdClaimKey(), Long.class);
	}

	private Claims getPayload(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload();
	}

	public boolean isAccessToken(String token) {
		return getPayload(token).get(JWT_CLAIM_KEY_TYPE, String.class).equals(JWT_CLAIM_VALUE_ACCESS_TOKEN);
	}

	public boolean isRefreshToken(String token) {
		return getPayload(token).get(JWT_CLAIM_KEY_TYPE, String.class).equals(JWT_CLAIM_VALUE_REFRESH_TOKEN);
	}
}
