package com.example.backend.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public BoardGame selectedGame;

    @ManyToMany
    public Set<BoardGame> suggestedGames;

    @OneToMany
    public Set<Participant> participants;
}
