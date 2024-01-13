package com.example.backend.services.boardgame.impl;

import java.util.List;
import java.util.Optional;

import com.example.backend.builders.BoardGameBuilder;
import com.example.backend.models.dtos.BoardGameRequest;
import lombok.val;
import org.springframework.stereotype.Service;

import com.example.backend.models.BoardGame;
import com.example.backend.repositories.BoardGameRepository;
import com.example.backend.services.boardgame.BoardGameService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BoardGameServiceImpl implements BoardGameService {
    private final BoardGameRepository boardgameRepository;

    @Override
    public List<BoardGame> getAllBoardGames() {
        return boardgameRepository.findAll();
    }

    @Override
    public Optional<BoardGame> getBoardGame(Long id) {
        return boardgameRepository.findById(id);
    }

    @Override
    public BoardGame addBoardGame(BoardGameRequest request) {
        val boardGame = new BoardGameBuilder()
                .name(request.getName())
                .minimumPlayers(request.getMinimumPlayers())
                .maximumPlayers(request.getMaximumPlayers())
                .minimumAgeRequirement(request.getMinimumAgeRequirement())
                .estimatedPlayTime(request.getEstimatedPlayTime())
                .htmlRules(request.getHtmlRules())
                .build();
        return boardgameRepository.save(boardGame);
    }
}
