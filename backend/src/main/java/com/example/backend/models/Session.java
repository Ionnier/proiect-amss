package com.example.backend.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@Entity(name = "sessions")
@Table(uniqueConstraints = @UniqueConstraint(columnNames={"id"}))
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String location;
    public SessionState state;

    @ManyToOne
    public BoardGame selectedGame;

    @OneToMany
    public Set<BoardGame> suggestedGames;

    @OneToMany
    public Set<Participant> participants;
}
