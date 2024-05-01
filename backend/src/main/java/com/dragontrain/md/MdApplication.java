package com.dragontrain.md;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan("com.dragontrain.md")
@SpringBootApplication
public class MdApplication {

	public static void main(String[] args) {
		SpringApplication.run(MdApplication.class, args);
	}

}
