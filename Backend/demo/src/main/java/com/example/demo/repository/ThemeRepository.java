package com.example.demo.repository;

import com.example.demo.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepository extends JpaRepository<Theme, Long> {
}
