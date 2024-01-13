package com.example.backend.services.user;

import com.example.backend.models.User;
import jakarta.annotation.Nullable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    @Nullable
    User getByUsername(String username);
    void saveUser(User user);
}

