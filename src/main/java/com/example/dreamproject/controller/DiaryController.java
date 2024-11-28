package com.example.dreamproject.controller;

import com.example.dreamproject.entity.Diary;
import com.example.dreamproject.entity.User;
import com.example.dreamproject.service.DiaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    // 꿈 기록 생성
    @PostMapping("/create")
    public ResponseEntity<Diary> createDiary(
            @RequestParam String title,
            @RequestParam String content,
            Principal principal) {
        // 현재 로그인된 사용자
        User user = getUserFromPrincipal(principal);

        Diary diary = diaryService.createDiary(user, title, content);
        return ResponseEntity.ok(diary);
    }

    // 사용자별 꿈 기록 조회
    @GetMapping
    public ResponseEntity<List<Diary>> getUserDiaries(Principal principal) {
        // 현재 로그인된 사용자
        User user = getUserFromPrincipal(principal);

        List<Diary> diaries = diaryService.getDiariesByUser(user);
        return ResponseEntity.ok(diaries);
    }

    // 꿈 기록 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDiary(@PathVariable Long id) {
        diaryService.deleteDiary(id);
        return ResponseEntity.ok("Diary deleted successfully!");
    }

    // Principal로부터 User 객체 가져오기 (예시)
    private User getUserFromPrincipal(Principal principal) {
        // TODO: Principal로부터 User를 가져오는 로직 추가
        return new User(); // 예시용
    }
}
