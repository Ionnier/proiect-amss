package com.example.backend.controllers;

import com.example.backend.models.dtos.LoginRequest;
import com.example.backend.models.dtos.SignupRequest;
import com.example.backend.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/api/signup")
    public String registerUser(@RequestBody @Valid SignupRequest signupRequest) {
        System.out.println(signupRequest.toString());
        return authenticationService.signUp(signupRequest);
    }

    @PostMapping("/api/login")
    public String loginUser(@RequestBody @Valid LoginRequest request) {
        return authenticationService.login(request);
    }

    @GetMapping("/api/hey")
    public String loginUser() {
        return "heyo";
    }
}

