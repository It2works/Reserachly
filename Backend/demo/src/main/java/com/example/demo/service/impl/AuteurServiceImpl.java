package com.example.demo.service.impl;

import com.example.demo.model.Auteur;
import com.example.demo.repository.AuteurRepository;
import com.example.demo.service.AuteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuteurServiceImpl implements AuteurService {

    @Autowired
    private AuteurRepository auteurRepository;

    @Override
    public Auteur createAuteur(Auteur auteur) {
        return auteurRepository.save(auteur);
    }

    @Override
    public List<Auteur> getAllAuteurs() {
        return auteurRepository.findAll();
    }

    @Override
    public Auteur getAuteurById(Long id) {
        return auteurRepository.findById(id).orElse(null);
    }

    @Override
    public Auteur updateAuteur(Long id, Auteur auteur) {
        Optional<Auteur> existingAuteur = auteurRepository.findById(id);
        if (existingAuteur.isPresent()) {
            auteur.setId(id);
            return auteurRepository.save(auteur);
        }
        return null;
    }

    @Override
    public void deleteAuteur(Long id) {
        auteurRepository.deleteById(id);
    }
}
