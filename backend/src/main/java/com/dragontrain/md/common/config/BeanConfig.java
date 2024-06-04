package com.dragontrain.md.common.config;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;

@RequiredArgsConstructor
@Configuration
public class BeanConfig {

	private final EntityManager em;

	@Bean
	public JPAQueryFactory jpaQueryFactory() {
		return new JPAQueryFactory(em);
	}


	@Qualifier("caffeineCacheManager")
	@Bean(name = "caffeineCacheManager")
	public CacheManager caffeineCacheManager() {
		SimpleCacheManager cacheManager = new SimpleCacheManager();

		List<CaffeineCache> caches =
			Arrays.stream(CacheType.values())
				.map(
					cache ->
						new CaffeineCache(
							cache.getName(),
							Caffeine.newBuilder()
								.expireAfterWrite(cache.getExpireAfterWrite(), TimeUnit.SECONDS)
								.maximumSize(cache.getMaximumSize())
								.recordStats()
								.build()))
				.toList();

		cacheManager.setCaches(caches);
		return cacheManager;
	}




}
