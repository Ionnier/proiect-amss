package com.example.backend.models;

import jakarta.persistence.*;
import lombok.Getter;

@Entity(name = "boardgames")
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"}),
                @UniqueConstraint(columnNames = {"name"}),
        }
)
public class BoardGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    public Long id;

    public String name;
    public Integer minimumPlayers;
    public Integer maximumPlayers;
    public Integer minimumAgeRequirement;
    public Integer estimatedPlayTime;
    public String htmlRules;
}
