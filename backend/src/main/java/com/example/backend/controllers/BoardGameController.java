package com.example.backend.controllers;

import com.example.backend.models.dtos.BoardGameRequest;
import jakarta.annotation.Nullable;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.models.BoardGame;
import com.example.backend.services.boardgame.BoardGameService;

import lombok.AllArgsConstructor;
import lombok.val;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@AllArgsConstructor
public class BoardGameController {
    private final BoardGameService boardgameService;

    @GetMapping(value = {"boardgame", "boardgame/{id}"})
    public List<BoardGame> getBoardGame(@PathVariable(required = false) @Nullable Long id) {
        if (id == null) {
            return boardgameService.getAllBoardGames();
        }

        val boardGame = boardgameService.getBoardGame(id);

        return boardGame.map(List::of).orElseGet(List::of);
    }

    @PostMapping("boardgame")
    public BoardGame createBoardGame(@RequestBody BoardGameRequest request) {
        return boardgameService.addBoardGame(request);
    }
}
