package com.example.demo.controller;

import com.example.demo.model.Administrateur;
import com.example.demo.repository.AdministrateurRepository;
import com.example.payload.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private AdministrateurRepository adminRepository;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Administrateur admin = adminRepository.findByEmail(loginRequest.getEmail())
                .orElse(null);

        if (admin == null) {
            return ResponseEntity.status(401).body("Invalid email or password (admin not found)");
        }

        if (!admin.getMotDePasse().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(401).body("Invalid email or password (wrong password)");
        }

        // Return admin ID along with success message
        return ResponseEntity.ok().body(admin.getId());
    }
}
