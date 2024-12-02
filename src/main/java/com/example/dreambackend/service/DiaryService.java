package com.example.dreambackend.service;

import java.util.Date;

import com.example.dreambackend.entity.Diary;
import com.example.dreambackend.entity.AppUser;
import com.example.dreambackend.repository.DiaryRepository;
import com.example.dreambackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;

    public DiaryService(DiaryRepository diaryRepository, UserRepository userRepository) {
        this.diaryRepository = diaryRepository;
        this.userRepository = userRepository;
    }

    public Diary createDiary(Long userId, String title, String content) {
        AppUser user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Diary diary = new Diary();
        diary.setUser(user);
        diary.setTitle(title);
        diary.setContent(content);
        return diaryRepository.save(diary);
    }

    public List<Diary> getDiaries(Long userId) {
        return diaryRepository.findByUserId(userId);
    }

    public void deleteDiary(Long diaryId) {
        diaryRepository.deleteById(diaryId);
    }

    public Diary updateDiary(Long diaryId, String title, String content) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(() -> new IllegalArgumentException("Diary not found"));
        diary.setTitle(title);
        diary.setContent(content);
        diary.setUpdatedAt(new Date());
        return diaryRepository.save(diary);
    }
}
