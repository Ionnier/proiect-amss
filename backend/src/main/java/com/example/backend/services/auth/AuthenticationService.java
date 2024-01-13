package com.example.backend.services.auth;

import com.example.backend.models.User;
import com.example.backend.models.dtos.LoginRequest;
import com.example.backend.models.dtos.SignupRequest;
import com.example.backend.models.mappers.UserMapper;

import com.example.backend.services.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserMapper mapper;

    public String signUp(SignupRequest signupRequest) {
        User user = mapper.signupRequestToUser(signupRequest);
        user.obfuscatePassword(passwordEncoder);
        userService.saveUser(user);
        return jwtService.generateToken(user.getUsername());
    }

    public String login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        val user = userService.getByUsername(loginRequest.getUsername());
        if (user == null) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        return jwtService.generateToken(user.getUsername());
    }
}