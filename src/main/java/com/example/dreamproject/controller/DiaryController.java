package com.example.dreamproject.controller;

import com.example.dreamproject.entity.Diary;
import com.example.dreamproject.service.DiaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diary")
public class DiaryController {

    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    // 다이어리 생성
    @PostMapping
    public ResponseEntity<Diary> createDiary(@RequestBody Diary diary, @RequestParam String username) {
        diary.setUsername(username); // 작성자 설정
        return ResponseEntity.ok(diaryService.createDiary(diary));
    }

    // 본인의 다이어리 목록 조회
    @GetMapping
    public ResponseEntity<List<Diary>> getDiaries(@RequestParam String username) {
        return ResponseEntity.ok(diaryService.getDiariesByUser(username));
    }

    // 특정 다이어리 조회
    @GetMapping("/{id}")
    public ResponseEntity<Diary> getDiary(@PathVariable Long id, @RequestParam String username) {
        return ResponseEntity.ok(diaryService.getDiaryById(id, username));
    }

    // 다이어리 수정
    @PutMapping("/{id}")
    public ResponseEntity<Diary> updateDiary(@PathVariable Long id, @RequestBody Diary diary, @RequestParam String username) {
        return ResponseEntity.ok(diaryService.updateDiary(id, diary, username));
    }

    // 다이어리 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDiary(@PathVariable Long id, @RequestParam String username) {
        diaryService.deleteDiary(id, username);
        return ResponseEntity.ok("Diary deleted successfully.");
    }
}
