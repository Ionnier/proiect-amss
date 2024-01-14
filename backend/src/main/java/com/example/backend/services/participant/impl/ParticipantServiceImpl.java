package com.example.backend.services.participant.impl;

import com.example.backend.models.Participant;
import com.example.backend.models.User;
import com.example.backend.models.UserState;
import com.example.backend.repositories.ParticipantRepository;
import com.example.backend.services.participant.ParticipantService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {
    private ParticipantRepository participantRepository;
    @Override
    public Participant createParticipant(User user, UserState userState) {
        val participant = Participant.builder().user(user).userState(userState).build();

        return participantRepository.save(participant);
    }

    @Override
    public Participant saveParticipant(Participant participant) {
        return participantRepository.save(participant);
    }
}
