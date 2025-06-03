package com.example.demo.controller;

import com.example.demo.model.Administrateur;
import com.example.demo.service.AdministrateurService;

import jakarta.validation.Valid; // ✅ Don't forget this import

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administrateurs")
public class AdministrateurController {

    @Autowired
    private AdministrateurService administrateurService;

    @PostMapping
    public ResponseEntity<Administrateur> createAdministrateur(@Valid @RequestBody Administrateur administrateur) {
        Administrateur createdAdministrateur = administrateurService.createAdministrateur(administrateur);
        return new ResponseEntity<>(createdAdministrateur, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Administrateur>> getAllAdministrateurs() {
        List<Administrateur> administrateurs = administrateurService.getAllAdministrateurs();
        return new ResponseEntity<>(administrateurs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Administrateur> getAdministrateurById(@PathVariable Long id) {
        Administrateur administrateur = administrateurService.getAdministrateurById(id);
        if (administrateur != null) {
            return new ResponseEntity<>(administrateur, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Administrateur> updateAdministrateur(@PathVariable Long id,
            @Valid @RequestBody Administrateur administrateur) {
        Administrateur updatedAdministrateur = administrateurService.updateAdministrateur(id, administrateur);
        if (updatedAdministrateur != null) {
            return new ResponseEntity<>(updatedAdministrateur, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdministrateur(@PathVariable Long id) {
        administrateurService.deleteAdministrateur(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
