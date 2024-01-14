package com.example.backend.controllers;

import com.example.backend.models.Session;
import com.example.backend.models.dtos.SessionAddSuggestedGameRequest;
import com.example.backend.models.dtos.SessionRequest;
import com.example.backend.services.auth.JwtService;
import com.example.backend.services.session.SessionService;
import com.example.backend.services.user.UserService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class SessionController {
    private final SessionService sessionService;
    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping("session")
    public List<Session> getSessions() {
        return sessionService.getAllSessions();
    }

    @GetMapping("session/{id}")
    public ResponseEntity<Session> getSession(@PathVariable @NonNull Long id) {
        return sessionService.getSession(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("session")
    ResponseEntity<Session> createSession(
            @RequestBody @NonNull SessionRequest request,
            @RequestHeader (name="Authorization") String bearerToken
    ) {
        val username = jwtService.extractUsername(bearerToken.substring(7));
        val user = userService.getByUsername(username);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(sessionService.createSession(request, user));
    }

    @PostMapping("session/{id}/join")
    ResponseEntity<Session> joinSession(
            @PathVariable @NonNull Long id,
            @RequestHeader (name="Authorization") String bearerToken
    ) {
        val username = jwtService.extractUsername(bearerToken.substring(7));
        val user = userService.getByUsername(username);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return sessionService.joinSession(id, user)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PatchMapping("session")
    ResponseEntity<Session> addSuggestedGame(
            @RequestBody @NonNull SessionAddSuggestedGameRequest request,
            @RequestHeader (name="Authorization") String bearerToken
    ) {
        val username = jwtService.extractUsername(bearerToken.substring(7));
        val user = userService.getByUsername(username);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return sessionService.addSuggestedGame(request, user)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PutMapping("session")
    ResponseEntity<Session> pickGame(
            @RequestBody @NonNull SessionAddSuggestedGameRequest request,
            @RequestHeader (name="Authorization") String bearerToken
    ) {
        val username = jwtService.extractUsername(bearerToken.substring(7));
        val user = userService.getByUsername(username);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        return sessionService.pickGame(request, user)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PostMapping("session/{id}/close")
    ResponseEntity<Session> closeSession(
            @PathVariable @NonNull Long id,
            @RequestHeader (name="Authorization") String bearerToken
    ) {
        val username = jwtService.extractUsername(bearerToken.substring(7));
        val user = userService.getByUsername(username);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return sessionService.closeSession(id, user)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
}
