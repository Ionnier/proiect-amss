package com.example.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "boardgames")
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"}),
                @UniqueConstraint(columnNames = {"name"}),
        }
)
@AllArgsConstructor
@NoArgsConstructor
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
    @Column(columnDefinition="TEXT", length = 102020)
    public String htmlRules;
}
