package com.example.backend;

import com.example.backend.models.User;
import com.example.backend.models.dtos.LoginRequest;
import com.example.backend.models.mappers.UserMapper;
import org.apache.juli.logging.Log;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

@SpringBootTest
class BackendApplicationTests {
	@Autowired
	UserMapper userMapper;

	@Test
	void contextLoads() {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail("asd");
		loginRequest.setPassword("asd");
		User user = userMapper.convert(loginRequest);
		assert Objects.equals(user.getUsername(), loginRequest.getEmail());
		assert Objects.equals(user.getPassword(), loginRequest.getPassword());
	}

}
