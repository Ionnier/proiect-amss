package com.example.backend.services.participant;

import com.example.backend.models.Participant;
import com.example.backend.models.User;
import com.example.backend.models.UserState;

public interface ParticipantService {
    Participant createParticipant(User user, UserState userState);
    Participant saveParticipant(Participant participant);
}
