package com.example.backend.models.mappers;

import com.example.backend.models.BoardGame;
import com.example.backend.models.dtos.BoardGameRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BoardGameMapper {
    @Mapping(target = "id", ignore = true)
    BoardGame boardGameRequestToBoardGame(BoardGameRequest request);
}
