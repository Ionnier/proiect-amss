package com.example.backend.configuration;

import com.example.backend.models.BoardGame;
import com.example.backend.repositories.BoardGameRepository;
import com.example.backend.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class InitializationConfig {
    private final DataSourceProperties dataSourceProperties;
    private final BoardGameRepository boardGameRepository;
    private final SessionRepository sessionRepository;


    @Value("classpath:static/monopoly_rules.html")
    private Resource resource;

    @Value("classpath:static/catan.html")
    private Resource resource2;


    @Bean
    public CommandLineRunner dataLoader() {
        return args -> {

            String dataSourceUrl = dataSourceProperties.determineUrl();

            if (!StringUtils.hasText(dataSourceUrl) && !dataSourceUrl.contains("mem") && !dataSourceUrl.contains("h2")) {
                return;
            }
            sessionRepository.deleteAll();
            boardGameRepository.deleteAll();



            boardGameRepository.save(new BoardGame(null, "Monopoly", 2, 8, 6, 30, new BufferedReader(new FileReader(resource.getFile())).lines().collect(Collectors.joining(""))));
            boardGameRepository.save(new BoardGame(null, "Catan", 2, 4, 12, 30, new BufferedReader(new FileReader(resource2.getFile())).lines().collect(Collectors.joining(""))));
        };
    }
}
