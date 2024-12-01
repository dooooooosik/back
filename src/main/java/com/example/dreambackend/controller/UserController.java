package com.example.dreambackend.controller;

import com.example.dreambackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password) {
        try {
            userService.registerUser(username, email, password);
            return ResponseEntity.status(201).body("User registered successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(
            @RequestParam String email,
            @RequestParam String password) {
        return userService.loginUser(email, password)
                ? ResponseEntity.ok("Login successful")
                : ResponseEntity.status(401).body("Invalid credentials");
    }
}
