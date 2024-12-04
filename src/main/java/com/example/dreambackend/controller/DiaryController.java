package com.example.dreambackend.controller;

import com.example.dreambackend.entity.Diary;
import com.example.dreambackend.service.DiaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5500")
@RestController
@RequestMapping("/api/diaries")
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping
    public ResponseEntity<?> createDiary(Authentication authentication, @RequestBody DiaryRequest request) {
        try {
            Long userId = Long.valueOf(authentication.getName());
            Diary diary = diaryService.createDiary(userId, request.getTitle(), request.getContent(), request.getCreatedAt());
            return ResponseEntity.ok(diary);
        } catch (Exception e) {
            e.printStackTrace(); // 에러 로그 출력
            return ResponseEntity.status(500).body("게시글 작성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }


    @GetMapping
    public ResponseEntity<?> getUserDiaries(Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName()); // 사용자 ID 가져오기
        return ResponseEntity.ok(diaryService.getDiaries(userId));
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<?> deleteDiary(Authentication authentication, @PathVariable Long diaryId) {
        Long userId = Long.valueOf(authentication.getName());
        diaryService.deleteDiary(userId, diaryId);
        return ResponseEntity.noContent().build();
    }
}

// DiaryRequest: 요청 본문에서 title과 content를 처리하는 DTO 클래스
class DiaryRequest {
    private String title;
    private String content;
    private String createdAt; // 날짜 필드 추가

    // Getters and Setters
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
