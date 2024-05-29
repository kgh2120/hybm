package com.dragontrain.md.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties("secret.exp")
public class ExpProperties {

	private final int eatenAmount;
	private final int registerAmount;
	private final int gotBadgeAmount;
}
