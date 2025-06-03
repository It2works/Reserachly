package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Theme")
    private Long id;

    @NotBlank(message = "Le nom du thème est obligatoire")
    @Column(name = "nom_theme", nullable = false)
    private String nomTheme;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomTheme() {
        return nomTheme;
    }

    public void setNomTheme(String nomTheme) {
        this.nomTheme = nomTheme;
    }
}
