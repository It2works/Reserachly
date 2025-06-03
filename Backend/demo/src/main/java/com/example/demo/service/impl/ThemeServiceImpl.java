package com.example.demo.service.impl;

import com.example.demo.model.Theme;
import com.example.demo.repository.ThemeRepository;
import com.example.demo.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThemeServiceImpl implements ThemeService {

    @Autowired
    private ThemeRepository themeRepository;

    @Override
    public Theme createTheme(Theme theme) {
        return themeRepository.save(theme);
    }

    @Override
    public List<Theme> getAllThemes() {
        return themeRepository.findAll();
    }

    @Override
    public Theme getThemeById(Long id) {
        return themeRepository.findById(id).orElse(null);
    }

    @Override
    public Theme updateTheme(Long id, Theme theme) {
        Optional<Theme> existingTheme = themeRepository.findById(id);
        if (existingTheme.isPresent()) {
            theme.setId(id);
            return themeRepository.save(theme);
        }
        return null;
    }

    @Override
    public void deleteTheme(Long id) {
        themeRepository.deleteById(id);
    }
}
