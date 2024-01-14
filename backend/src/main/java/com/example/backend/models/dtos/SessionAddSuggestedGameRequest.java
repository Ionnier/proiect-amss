package com.example.backend.models.dtos;

import jakarta.validation.constraints.NotNull;

public record SessionAddSuggestedGameRequest(@NotNull Long gameId, @NotNull Long sessionId) { }
