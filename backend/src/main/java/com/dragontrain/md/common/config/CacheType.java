package com.dragontrain.md.common.config;

import lombok.Getter;

@Getter
public enum CacheType {


	CACTEGORY("category");

	private String name;
	private Integer expireAfterWrite;
	private Integer maximumSize;

	CacheType(String name) {
		this.name = name;
		this.expireAfterWrite = ConstConfig.DEFAULT_TTL_SEC;
		this.maximumSize = ConstConfig.DEFAULT_MAX_SIZE;
	}

	static class ConstConfig {
		static final Integer DEFAULT_TTL_SEC = 600;
		static final Integer DEFAULT_MAX_SIZE = 10240;
	}
}
