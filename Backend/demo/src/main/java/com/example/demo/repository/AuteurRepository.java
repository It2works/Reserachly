package com.example.demo.repository;

import com.example.demo.model.Auteur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuteurRepository extends JpaRepository<Auteur, Long> {
}
