package com.example.backend.services.commend.impl;

import com.example.backend.models.Participant;
import com.example.backend.models.SessionState;
import com.example.backend.models.User;
import com.example.backend.services.commend.CommendService;
import com.example.backend.services.participant.ParticipantService;
import com.example.backend.services.session.SessionService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CommendServiceImpl implements CommendService {
    private final SessionService sessionService;
    private final ParticipantService participantService;

    @Override
    public Optional<Participant> commend(Long sessionId, Long userId, User commendingUser) {
        val session = sessionService.getSession(sessionId);

        if (session.isEmpty()
                || session.get().participants.stream().noneMatch(participant -> participant.user.id.equals(userId))
                || session.get().participants.stream().noneMatch(participant -> participant.user.id.equals(commendingUser.id))
                || userId.equals(commendingUser.id)
                || session.get().state != SessionState.FINISHED
        ) return Optional.empty();

        val commendedParticipant = session.get().participants.stream().filter(participant -> participant.user.id.equals(userId))
                .findFirst().get();
        commendedParticipant.commendedBy.add(commendingUser);

        return Optional.of(participantService.saveParticipant(commendedParticipant));
    }
}
