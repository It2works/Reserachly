package com.example.demo.service;

import com.example.demo.model.Theme;
import java.util.List;

public interface ThemeService {
    Theme createTheme(Theme theme);

    List<Theme> getAllThemes();

    Theme getThemeById(Long id);

    Theme updateTheme(Long id, Theme theme);

    void deleteTheme(Long id);
}
