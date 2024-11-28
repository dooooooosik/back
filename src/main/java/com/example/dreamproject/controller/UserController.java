package com.example.dreamproject.controller;

import com.example.dreamproject.entity.User;
import com.example.dreamproject.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET 요청 방지 (회원가입은 POST로만 처리)
    @GetMapping("/register")
    public ResponseEntity<String> handleBrowserRequest() {
        return ResponseEntity.badRequest().body("GET method is not supported for this endpoint. Use POST instead.");
    }

    // 회원가입 API
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
        try {
            userService.registerUser(user); // User 엔티티 직접 사용
            return ResponseEntity.ok("Registration successful");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 로그인 API
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        try {
            User authenticatedUser = userService.validateLogin(user.getUsername(), user.getPassword());
            return ResponseEntity.ok("Welcome, " + authenticatedUser.getUsername() + "!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}
