package com.example.demo.service.impl;

import com.example.demo.model.Administrateur;
import com.example.demo.repository.AdministrateurRepository;
import com.example.demo.service.AdministrateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministrateurServiceImpl implements AdministrateurService {

    @Autowired
    private AdministrateurRepository administrateurRepository;

    @Override
    public Administrateur createAdministrateur(Administrateur administrateur) {
        return administrateurRepository.save(administrateur);
    }

    @Override
    public List<Administrateur> getAllAdministrateurs() {
        return administrateurRepository.findAll();
    }

    @Override
    public Administrateur getAdministrateurById(Long id) {
        return administrateurRepository.findById(id).orElse(null);
    }

    @Override
    public Administrateur updateAdministrateur(Long id, Administrateur administrateur) {
        Optional<Administrateur> existingAdmin = administrateurRepository.findById(id);
        if (existingAdmin.isPresent()) {
            administrateur.setId(id);
            return administrateurRepository.save(administrateur);
        }
        return null;
    }

    @Override
    public void deleteAdministrateur(Long id) {
        administrateurRepository.deleteById(id);
    }
}
