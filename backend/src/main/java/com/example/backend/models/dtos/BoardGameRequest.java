package com.example.backend.models.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BoardGameRequest {
    @NotBlank
    String name;
    Integer minimumPlayers;
    Integer maximumPlayers;
    Integer minimumAgeRequirement;
    Integer estimatedPlayTime;
    String htmlRules;
}
