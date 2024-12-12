package com.example.dreambackend.controller;

import com.example.dreambackend.service.GPTService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dream")
public class DreamController {
    private final GPTService gptService;

    public DreamController(GPTService gptService) {
        this.gptService = gptService;
    }

    @GetMapping("/search")
    public ResponseEntity<String> searchDream(@RequestParam String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("키워드를 입력해주세요.");
        }

        try {
            String interpretation = gptService.getDreamInterpretation(keyword.trim());
            return ResponseEntity.ok(interpretation);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("서버 오류 발생: " + e.getMessage());
        }
    }
}
