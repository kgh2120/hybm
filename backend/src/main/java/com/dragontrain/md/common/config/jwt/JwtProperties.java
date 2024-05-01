package com.dragontrain.md.common.config.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties("secret.jwt")
public class JwtProperties {
	private final long accessTokenTtl;
	private final long refreshTokenTtl;
	private final String secretKey;
	private final String userIdClaimKey;
}
