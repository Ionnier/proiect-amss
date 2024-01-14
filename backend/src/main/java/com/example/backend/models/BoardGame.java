package com.example.backend.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;
import java.util.Set;

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
