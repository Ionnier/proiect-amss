package com.example.backend.controllers;

import com.example.backend.models.Participant;
import com.example.backend.models.dtos.ReportRequest;
import com.example.backend.services.auth.JwtService;
import com.example.backend.services.report.ReportService;
import com.example.backend.services.user.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ReportController {
    private final JwtService jwtService;
    private final UserService userService;
    private final ReportService reportService;
    @PostMapping("session/{sessionId}/report/{userId}")
    ResponseEntity<Participant> commendPlayer(
            @PathVariable Long sessionId,
            @PathVariable Long userId,
            @RequestHeader(name="Authorization") String bearerToken,
            @RequestBody @Valid ReportRequest reportRequest) {
        val username = jwtService.extractUsername(bearerToken.substring(7));
        val user = userService.getByUsername(username);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return reportService.report(sessionId, userId, user, reportRequest.reason())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
}
