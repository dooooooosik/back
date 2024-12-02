package com.example.dreambackend.controller;

import com.example.dreambackend.entity.Diary;
import com.example.dreambackend.service.DiaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diaries")
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping
    public ResponseEntity<Diary> createDiary(Authentication authentication,
                                             @RequestParam String title,
                                             @RequestParam String content) {
        Long userId = Long.valueOf(authentication.getName());
        Diary diary = diaryService.createDiary(userId, title, content);
        return ResponseEntity.ok(diary);
    }

    @GetMapping
    public ResponseEntity<List<Diary>> getDiaries(Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        return ResponseEntity.ok(diaryService.getDiaries(userId));
    }

    @PutMapping("/{diaryId}")
    public ResponseEntity<Diary> updateDiary(@PathVariable Long diaryId,
                                             @RequestParam String title,
                                             @RequestParam String content) {
        return ResponseEntity.ok(diaryService.updateDiary(diaryId, title, content));
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<Void> deleteDiary(@PathVariable Long diaryId) {
        diaryService.deleteDiary(diaryId);
        return ResponseEntity.noContent().build();
    }
}
