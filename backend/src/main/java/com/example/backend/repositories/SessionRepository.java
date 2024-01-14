package com.example.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.models.Session;

public interface SessionRepository extends JpaRepository<Session, Long> { }
