package com.dragontrain.md;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest
class MdApplicationTests {

	@Autowired
	Environment env;

	@Test
	void contextLoads() {
	}

}
