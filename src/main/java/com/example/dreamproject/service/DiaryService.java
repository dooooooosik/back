package com.example.dreamproject.service;

import com.example.dreamproject.entity.Diary;
import com.example.dreamproject.entity.User;
import com.example.dreamproject.repository.DiaryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public Diary createDiary(User user, String title, String content) {
        Diary diary = new Diary();
        diary.setUser(user);
        diary.setTitle(title);
        diary.setContent(content);
        diary.setCreatedAt(LocalDateTime.now());
        return diaryRepository.save(diary);
    }

    public List<Diary> getDiariesByUser(User user) {
        return diaryRepository.findByUser(user);
    }

    public void deleteDiary(Long id) {
        diaryRepository.deleteById(id);
    }
}
