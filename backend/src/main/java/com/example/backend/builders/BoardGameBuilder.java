package com.example.backend.builders;

import com.example.backend.models.BoardGame;

public class BoardGameBuilder {

    private Long id = null;
    private  String name;
    private Integer minimumPlayers;
    private Integer maximumPlayers;
    private Integer minimumAgeRequirement;
    private Integer estimatedPlayTime;
    private String htmlRules;

    public BoardGameBuilder() {}

    public BoardGame build() {
        var boardGame = new BoardGame();
        boardGame.id = id;
                boardGame.name = name;
                boardGame.minimumPlayers = minimumPlayers;
                boardGame.maximumPlayers = maximumPlayers;
                boardGame.minimumAgeRequirement = minimumAgeRequirement;
                boardGame.estimatedPlayTime = estimatedPlayTime;
                boardGame.htmlRules = htmlRules;

        return boardGame;
    }

    public BoardGameBuilder name(String name) {
        this.name = name;
        return this;
    }

    public BoardGameBuilder minimumPlayers(Integer minimumPlayers) {
        this.minimumPlayers = minimumPlayers;
        return this;
    }

    public BoardGameBuilder maximumPlayers(Integer maximumPlayers) {
        this.maximumPlayers = maximumPlayers;
        return this;
    }

    public BoardGameBuilder minimumAgeRequirement(Integer minimumAgeRequirement) {
        this.minimumAgeRequirement = minimumAgeRequirement;
        return this;
    }

    public BoardGameBuilder estimatedPlayTime(Integer estimatedPlayTime) {
        this.estimatedPlayTime = estimatedPlayTime;
        return this;
    }

    public BoardGameBuilder htmlRules(String htmlRules) {
        this.htmlRules = htmlRules;
        return this;
    }

    public BoardGameBuilder copy(BoardGame boardGame){
        this.id = boardGame.id;
        this.name = boardGame.name;
        this.minimumPlayers = boardGame.minimumPlayers;
        this.maximumPlayers = boardGame.maximumPlayers;
        this.minimumAgeRequirement = boardGame.minimumAgeRequirement;
        this.estimatedPlayTime = boardGame.estimatedPlayTime;
        this.htmlRules = boardGame.htmlRules;

        return this;
    }
}
