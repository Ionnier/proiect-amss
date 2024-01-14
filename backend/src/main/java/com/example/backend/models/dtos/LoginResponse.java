package com.example.backend.models.dtos;

import com.example.backend.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginResponse(@NotBlank String token, @NotNull User user) {}
