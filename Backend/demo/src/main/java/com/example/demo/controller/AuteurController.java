package com.example.demo.controller;

import com.example.demo.model.Auteur;
import com.example.demo.service.AuteurService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auteurs")
public class AuteurController {

    @Autowired
    private AuteurService auteurService;

    @PostMapping
    public ResponseEntity<Auteur> createAuteur(@Valid @RequestBody Auteur auteur) {
        return new ResponseEntity<>(auteurService.createAuteur(auteur), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Auteur>> getAllAuteurs() {
        return ResponseEntity.ok(auteurService.getAllAuteurs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auteur> getAuteurById(@PathVariable Long id) {
        Auteur auteur = auteurService.getAuteurById(id);
        return auteur != null ? ResponseEntity.ok(auteur) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Auteur> updateAuteur(@PathVariable Long id, @Valid @RequestBody Auteur auteur) {
        Auteur updated = auteurService.updateAuteur(id, auteur);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuteur(@PathVariable Long id) {
        auteurService.deleteAuteur(id);
        return ResponseEntity.noContent().build();
    }
}
