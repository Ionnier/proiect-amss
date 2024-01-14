package com.example.backend.services.report;

import com.example.backend.models.Participant;
import com.example.backend.models.User;
import lombok.NonNull;

import java.util.Optional;

public interface ReportService {
    Optional<Participant> report(@NonNull Long sessionId, @NonNull Long userId, @NonNull User reportingUser, @NonNull String reason);
}
