package com.dragontrain.md.common.config.jwt;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.dragontrain.md.common.config.exception.GlobalErrorCode;
import com.dragontrain.md.common.config.exception.TokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
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
			.compact(), jwtProperties.getRefreshTokenTtl());
	}

	public Long parseUserId(String token) throws JwtException {
		return getPayload(token).get(jwtProperties.getUserIdClaimKey(), Long.class);
	}

	private Claims getPayload(String token) {
		try {
			return Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();
		} catch (JwtException e) {
			GlobalErrorCode errorCode = null;
			if (e instanceof MalformedJwtException) {
				errorCode = GlobalErrorCode.TOKEN_MALFORMED;
			} else if (e instanceof SignatureException) {
				errorCode = GlobalErrorCode.TOKEN_NOT_VERIFIED;
			} else if (e instanceof UnsupportedJwtException) {
				errorCode = GlobalErrorCode.TOKEN_UNSUPPORTED;
			} else if (e instanceof InvalidKeyException) {
				errorCode = GlobalErrorCode.TOKEN_INVALID_KEY;
			} else if (e instanceof ExpiredJwtException) {
				errorCode = GlobalErrorCode.TOKEN_EXPIRED;
			} else {
				errorCode = GlobalErrorCode.FORBIDDEN;
			}
			throw new TokenException(errorCode);
		}
	}

	public boolean isAccessToken(String token) {
		return getPayload(token).get(JWT_CLAIM_KEY_TYPE, String.class).equals(JWT_CLAIM_VALUE_ACCESS_TOKEN);
	}

	public boolean isRefreshToken(String token) {
		return getPayload(token).get(JWT_CLAIM_KEY_TYPE, String.class).equals(JWT_CLAIM_VALUE_REFRESH_TOKEN);
	}
}
