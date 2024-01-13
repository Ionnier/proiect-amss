package com.example.backend.models.mappers;

import com.example.backend.models.dtos.LoginRequest;
import com.example.backend.models.dtos.SignupRequest;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserMapperTests {
    @Autowired
    UserMapper userMapper;

    @Test
    void loginRequestMapsToUser() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("asd");
        loginRequest.setPassword("pswd");
        val user = userMapper.loginRequestToUser(loginRequest);
        assertEquals(loginRequest.getPassword(), user.getPassword());
        assertEquals(loginRequest.getUsername(), user.getUsername());
    }

    @Test
    void signupRequestMapsToUser() {
        var signupRequest = new SignupRequest();
        signupRequest.setEmail("test@email.com");
        signupRequest.setPassword("testpassword");
        signupRequest.setUsername("testUserName");

        val user = userMapper.signupRequestToUser(signupRequest);

        assertEquals(signupRequest.getUsername(), user.getUsername());
        assertEquals(signupRequest.getPassword(), user.getPassword());
        assertEquals(signupRequest.getEmail(), user.email);
    }
}
