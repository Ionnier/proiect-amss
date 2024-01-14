package com.example.backend.services.session.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.backend.models.*;
import com.example.backend.models.dtos.SessionAddSuggestedGameRequest;
import com.example.backend.models.dtos.SessionRequest;
import com.example.backend.models.mappers.SessionMapper;
import com.example.backend.repositories.ParticipantRepository;
import com.example.backend.services.boardgame.BoardGameService;
import com.example.backend.services.participant.ParticipantService;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;

import com.example.backend.repositories.SessionRepository;
import com.example.backend.services.session.SessionService;

import lombok.val;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SessionServiceImpl implements SessionService {
    private SessionMapper mapper;
    private SessionRepository sessionRepository;
    private BoardGameService boardGameService;
    private ParticipantService participantService;

    @Override
    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    @Override
    public Optional<Session> getSession(Long id) {
        return sessionRepository.findById(id);
    }

    @Override
    @Nonnull
    public Session createSession(SessionRequest request, User byUser) {
        val session =  mapper.sessionRequestToSession(request);

        val participant = participantService.createParticipant(byUser, UserState.HOST);

        session.participants = Set.of(participant);

        if (session.state == null) {
            session.state = SessionState.CREATED;
        }

        return sessionRepository.save(session);
    }

    @Override
    public Optional<Session> addSuggestedGame(SessionAddSuggestedGameRequest request, User user) {
        val session = sessionRepository.findById(request.sessionId());
        val boardGame = boardGameService.getBoardGame(request.gameId());

        if (session.isEmpty() || boardGame.isEmpty()) {
            System.out.println("Session or boardgame does not exist.");
            return Optional.empty();
        }

        val userInParticipants = session.get().participants.stream()
                .anyMatch(participant -> participant.user.id.equals(user.id));


        if (!userInParticipants || session.get().state != SessionState.CREATED) {
            System.out.println("User is not a participant, or session does not accept any more suggestions");
            return Optional.empty();
        }

        session.get().suggestedGames.add(boardGame.get());
        return Optional.of(sessionRepository.save(session.get()));
    }

    @Override
    public Optional<Session> joinSession(Long sessionId, User user) {
        val session = sessionRepository.findById(sessionId);

        if (session.isEmpty()
                || session.get().state == SessionState.CLOSED
                || session.get().state == SessionState.FINISHED
        ) {
            return Optional.empty();
        }

        if (session.get().state == SessionState.OPEN) {
            val participant = session.get().participants.stream()
                    .filter(participant1 -> participant1.user.id.equals(user.id))
                    .findFirst();

            if (participant.isPresent()) {
                participant.get().userState = UserState.ATTENDING;
                participantService.saveParticipant(participant.get());
            }

            return session;
        }

        UserState userState;
        if (session.get().state == SessionState.CREATED) {
            userState = UserState.TENTATIVE;
        } else {
            userState = UserState.ATTENDING;
        }

        val participant = participantService.createParticipant(user, userState);

        session.get().participants.add(participant);

        return Optional.of(sessionRepository.save(session.get()));
    }

    @Override
    public Optional<Session> pickGame(SessionAddSuggestedGameRequest request, User user) {
        val session = sessionRepository.findById(request.sessionId());

        if (session.isEmpty()) return Optional.empty();

        val participant = session.get().participants.stream()
                .filter(participant1 -> participant1.user.id.equals(user.id))
                .findFirst();

        if (participant.isEmpty() || participant.get().userState != UserState.HOST) {
            return Optional.empty();
        }

        val game = boardGameService.getBoardGame(request.gameId());
        if (game.isEmpty()) return Optional.empty();

        if (session.get().suggestedGames.stream()
                .noneMatch(suggestedGame -> suggestedGame.id.equals(game.get().id))
        ) {
            return Optional.empty();
        }

        val updatedSession = session.get();
        updatedSession.selectedGame = game.get();

        return Optional.of(sessionRepository.save(updatedSession));
    }

    @Override
    public Optional<Session> closeSession(Long sessionId, User user) {
        val session = sessionRepository.findById(sessionId);

        if (session.isEmpty()
        || session.get().participants.stream()
                .noneMatch(
                        participant -> participant.user.id.equals(user.id)
                        && participant.userState == UserState.HOST
                )){
            return Optional.empty();
        }

        SessionState newSessionState;
        if (session.get().state == SessionState.CLOSED) {
            newSessionState = SessionState.FINISHED;
        } else {
            newSessionState = SessionState.CLOSED;
        }

        val currentSession = session.get();
        currentSession.state = newSessionState;
        return Optional.of(sessionRepository.save(currentSession));
    }
}
