package com.example.backend.models.responses;

import com.example.backend.models.User;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;

public record LoginResponse(@NotBlank String token, @Nonnull User user) {}
