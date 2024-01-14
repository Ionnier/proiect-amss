package com.example.backend.models.mappers;

import com.example.backend.models.Participant;
import com.example.backend.models.User;
import com.example.backend.models.UserState;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ParticipantMapper {
    Participant userToParticipant(User user) {
        return Participant.builder().user(user).userState(UserState.TENTATIVE).build();
    }
}
