package com.example.backend.models.dtos;

import jakarta.validation.constraints.NotBlank;

public record ReportRequest(@NotBlank String reason) {
}
