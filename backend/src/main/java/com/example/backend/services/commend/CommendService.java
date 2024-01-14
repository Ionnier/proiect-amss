package com.example.backend.services.commend;

import com.example.backend.models.Participant;
import com.example.backend.models.User;

import java.util.Optional;

public interface CommendService {
    Optional<Participant> commend(Long sessionId, Long userId, User commendingUser);
}
