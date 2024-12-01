package com.example.dreambackend.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user") // 테이블 이름 매핑
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(nullable = false)
    private String role = "USER"; // 기본값 설정

    public AppUser() {
        this.createdAt = new Date(); // 현재 시간으로 초기화
    }

    public AppUser(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdAt = new Date();
        this.role = "USER"; // 기본값 설정
    }


    // Getters and Setters
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
