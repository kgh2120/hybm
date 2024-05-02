package com.dragontrain.md.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties("secret.crawl")
public class CrawlProperties {

	private final String baseUrl;
	private final String productNameSelector;
	private final String kanCodeNameSelector;
}
