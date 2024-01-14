package com.example.backend.services.boardgame.impl;

import java.util.List;
import java.util.Optional;

import com.example.backend.models.dtos.BoardGameRequest;
import com.example.backend.models.mappers.BoardGameMapper;
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
    private final BoardGameMapper mapper;

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
        val boardGame = mapper.boardGameRequestToBoardGame(request);
        return boardgameRepository.save(boardGame);
    }
}
