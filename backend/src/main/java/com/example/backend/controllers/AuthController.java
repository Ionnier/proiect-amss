package com.example.backend.controllers;

import com.example.backend.models.dtos.LoginRequest;
import com.example.backend.models.dtos.SignupRequest;
import com.example.backend.models.responses.LoginResponse;
import com.example.backend.services.auth.AuthenticationService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public LoginResponse registerUser(@RequestBody @Valid SignupRequest signupRequest) {
            return authenticationService.signUp(signupRequest);
    }

    @PostMapping("/login")
    public LoginResponse loginUser(@RequestBody @Valid LoginRequest request) {
        return authenticationService.login(request);
    }
}

