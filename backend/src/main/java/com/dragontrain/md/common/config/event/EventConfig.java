package com.dragontrain.md.common.config.event;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import lombok.RequiredArgsConstructor;

@EnableAsync
@RequiredArgsConstructor
@Configuration
public class EventConfig {

	private final ApplicationContext applicationContext;

	@Bean
	public InitializingBean eventInitializer() {
		return () -> Events.setEventPublisher(applicationContext);
	}

}
