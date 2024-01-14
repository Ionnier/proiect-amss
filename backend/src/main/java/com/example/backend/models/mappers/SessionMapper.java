package com.example.backend.models.mappers;

import com.example.backend.models.Participant;
import com.example.backend.models.Session;
import com.example.backend.models.User;
import com.example.backend.models.dtos.SessionRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SessionMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "selectedGame", ignore = true)
    Session sessionRequestToSession(SessionRequest request);
}
