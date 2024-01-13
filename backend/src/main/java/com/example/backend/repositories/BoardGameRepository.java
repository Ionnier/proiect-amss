package com.example.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.models.BoardGame;

public interface BoardGameRepository extends JpaRepository<BoardGame, Long> {

}
