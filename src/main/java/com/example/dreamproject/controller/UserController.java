package com.example.dreamproject.controller;

import com.example.dreamproject.entity.User;
import com.example.dreamproject.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 API
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User savedUser = userService.registerUser(user);
        return ResponseEntity.ok(savedUser);
    }

    // 로그인 API
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String username, @RequestParam String password) {
        try {
            User user = userService.validateLogin(username, password); // User 객체 반환
            return ResponseEntity.ok("Welcome, " + user.getUsername() + "!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Null");
        }
    }

}
