package com.example.payload;

public class LoginRequest {
    private String email;
    private String password;

    // Getters & Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
