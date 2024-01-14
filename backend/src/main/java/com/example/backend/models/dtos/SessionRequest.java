package com.example.backend.models.dtos;

import com.example.backend.models.BoardGame;
import com.example.backend.models.SessionState;
import com.example.backend.models.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Data
public class SessionRequest {
    @NotBlank
    String location;
    SessionState state;
    Set<BoardGame> suggestedGames;
    Set<User> users;
}
