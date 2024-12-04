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
        String interpretation = gptService.getDreamInterpretation(keyword);
        return ResponseEntity.ok(interpretation);
    }
}
