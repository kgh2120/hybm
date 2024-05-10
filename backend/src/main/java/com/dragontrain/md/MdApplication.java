package com.dragontrain.md;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@ConfigurationPropertiesScan("com.dragontrain.md")
@EnableScheduling
@SpringBootApplication
public class MdApplication {

	public static void main(String[] args) {
		SpringApplication.run(MdApplication.class, args);
	}

}
