package com.example.backend.repositories;

import com.example.backend.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> { }
