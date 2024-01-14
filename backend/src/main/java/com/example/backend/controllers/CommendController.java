package com.example.backend.controllers;

import com.example.backend.models.Participant;
import com.example.backend.services.auth.JwtService;
import com.example.backend.services.commend.CommendService;
import com.example.backend.services.user.UserService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class CommendController {
    private final JwtService jwtService;
    private final UserService userService;
    private final CommendService commendService;
    @PostMapping("session/{sessionId}/commend/{userId}")
    ResponseEntity<Participant> commendPlayer(
            @PathVariable Long sessionId,
            @PathVariable Long userId,
            @RequestHeader(name="Authorization") String bearerToken) {
        val username = jwtService.extractUsername(bearerToken.substring(7));
        val user = userService.getByUsername(username);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return commendService.commend(sessionId, userId, user)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
}
