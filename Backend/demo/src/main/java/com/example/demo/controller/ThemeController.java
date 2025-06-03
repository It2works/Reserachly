package com.example.demo.controller;

import com.example.demo.model.Theme;
import com.example.demo.service.ThemeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/themes")
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    @PostMapping
    public ResponseEntity<Theme> createTheme(@Valid @RequestBody Theme theme) {
        return ResponseEntity.status(201).body(themeService.createTheme(theme));
    }

    @GetMapping
    public ResponseEntity<List<Theme>> getAllThemes() {
        return ResponseEntity.ok(themeService.getAllThemes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Theme> getThemeById(@PathVariable Long id) {
        Theme theme = themeService.getThemeById(id);
        return theme != null ? ResponseEntity.ok(theme) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Theme> updateTheme(@PathVariable Long id, @Valid @RequestBody Theme theme) {
        Theme updated = themeService.updateTheme(id, theme);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        themeService.deleteTheme(id);
        return ResponseEntity.noContent().build();
    }
}
