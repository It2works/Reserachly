package com.example.demo.service;

import com.example.demo.model.Auteur;
import java.util.List;

public interface AuteurService {
    Auteur createAuteur(Auteur auteur);

    List<Auteur> getAllAuteurs();

    Auteur getAuteurById(Long id);

    Auteur updateAuteur(Long id, Auteur auteur);

    void deleteAuteur(Long id);
}
