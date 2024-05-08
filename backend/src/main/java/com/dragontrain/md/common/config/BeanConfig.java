package com.dragontrain.md.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class BeanConfig {

	private final EntityManager em;

	@Bean
	public JPAQueryFactory jpaQueryFactory(){
		return new JPAQueryFactory(em);
	}
}
