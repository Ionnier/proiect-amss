package com.example.backend.services.report.impl;

import com.example.backend.models.Participant;
import com.example.backend.models.Report;
import com.example.backend.models.SessionState;
import com.example.backend.models.User;
import com.example.backend.repositories.ReportRepository;
import com.example.backend.services.participant.ParticipantService;
import com.example.backend.services.report.ReportService;
import com.example.backend.services.session.SessionService;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final SessionService sessionService;
    private final ParticipantService participantService;
    private final ReportRepository reportRepository;

    @Override
    public Optional<Participant> report(Long sessionId, Long userId, User reportingUser, @Nonnull String reason) {
        val session = sessionService.getSession(sessionId);

        if (session.isEmpty()
                || session.get().participants.stream().noneMatch(participant -> participant.user.id.equals(userId))
                || session.get().participants.stream().noneMatch(participant -> participant.user.id.equals(reportingUser.id))
                || userId.equals(reportingUser.id)
                || session.get().state != SessionState.FINISHED
        ) return Optional.empty();

        val reportedParticipant = session.get().participants.stream().filter(participant -> participant.user.id.equals(userId))
                .findFirst().get();

        val report = reportRepository.save(Report.builder().user(reportingUser).reason(reason).build());
        reportedParticipant.reports.add(report);

        return Optional.of(participantService.saveParticipant(reportedParticipant));
    }
}
