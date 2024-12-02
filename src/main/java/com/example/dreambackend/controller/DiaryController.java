package com.example.dreambackend.controller;

import com.example.dreambackend.entity.Diary;
import com.example.dreambackend.service.DiaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/diaries")
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping
    public ResponseEntity<Diary> createDiary(Authentication authentication, @RequestBody DiaryRequest request) {
        // Authentication.getName()에서 사용자 ID를 가져옵니다.
        Long userId = Long.valueOf(authentication.getName());
        Diary diary = diaryService.createDiary(userId, request.getTitle(), request.getContent());
        return ResponseEntity.ok(diary);
    }

    @GetMapping
    public ResponseEntity<?> getUserDiaries(Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        return ResponseEntity.ok(diaryService.getDiaries(userId));
    }

    @PutMapping("/{diaryId}")
    public ResponseEntity<Diary> updateDiary(Authentication authentication, @PathVariable Long diaryId,
                                             @RequestBody DiaryRequest request) {
        Long userId = Long.valueOf(authentication.getName());
        Diary diary = diaryService.updateDiary(userId, diaryId, request.getTitle(), request.getContent());
        return ResponseEntity.ok(diary);
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
}
