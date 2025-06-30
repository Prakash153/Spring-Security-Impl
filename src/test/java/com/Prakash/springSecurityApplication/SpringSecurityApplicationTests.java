package com.Prakash.springSecurityApplication;

import com.Prakash.springSecurityApplication.entities.User;
import com.Prakash.springSecurityApplication.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringSecurityApplicationTests {

	@Autowired
	private JwtService jwtService;

	@Test
	void contextLoads() {

		User user = new User(4L, "Prakash@gmail.com", "Prakash123");

		String token = jwtService.generateToken(user);

		System.out.println(token);

		Long id = jwtService.getUserFromToken(token);

		System.out.println(id);


	}

}
