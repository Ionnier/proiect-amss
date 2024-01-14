package com.example.backend.services.session;

import java.util.List;
import java.util.Optional;

import com.example.backend.models.Session;
import com.example.backend.models.User;
import com.example.backend.models.dtos.SessionAddSuggestedGameRequest;
import com.example.backend.models.dtos.SessionRequest;
import jakarta.annotation.Nonnull;
import lombok.NonNull;

public interface SessionService {
    List<Session> getAllSessions();
    Optional<Session> getSession(Long id);

    @Nonnull
    Session createSession(SessionRequest request, User byUser);
    Optional<Session> addSuggestedGame(SessionAddSuggestedGameRequest request, User user);
    Optional<Session> joinSession(Long sessionId, User user);
    Optional<Session> pickGame(SessionAddSuggestedGameRequest request, User user);
    Optional<Session> closeSession(Long sessionId, User user);
}
