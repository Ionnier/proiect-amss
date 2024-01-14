package com.example.backend.services.boardgame;

import java.util.List;
import java.util.Optional;

import com.example.backend.models.BoardGame;
import com.example.backend.models.dtos.BoardGameRequest;

public interface BoardGameService {
    List<BoardGame> getAllBoardGames();
    Optional<BoardGame> getBoardGame(Long id);
    BoardGame addBoardGame(BoardGameRequest request);
}
