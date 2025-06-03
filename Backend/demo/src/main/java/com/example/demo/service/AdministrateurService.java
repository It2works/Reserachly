package com.example.demo.service;

import com.example.demo.model.Administrateur;
import java.util.List;

public interface AdministrateurService {
    Administrateur createAdministrateur(Administrateur administrateur);

    List<Administrateur> getAllAdministrateurs();

    Administrateur getAdministrateurById(Long id);

    Administrateur updateAdministrateur(Long id, Administrateur administrateur);

    void deleteAdministrateur(Long id);
}
