package com.example.backend.repositories;

import com.example.backend.models.User;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    @Nullable
    User findByEmail(String email);

    @Nullable
    User findByUsername(String username);
}
