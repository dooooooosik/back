package com.example.dreambackend.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user; // 🔥 user로 명확히 설정

    private String title; // 게시글 제목

    @Lob
    private String content; // 게시글 내용

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date(); // 생성 날짜

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt; // 수정 날짜

    public Long getId() {
        return id;
    }

    // 🔥 getUsername -> getUser()로 변경
    public String getUsername() {
        return user.getUsername(); // user의 username을 가져온다.
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user; // 🔥 setUser로 명확히 수정
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
