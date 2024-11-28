package com.example.dreamproject.service;

import com.example.dreamproject.entity.Diary;
import com.example.dreamproject.repository.DiaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    // 다이어리 생성
    public Diary createDiary(Diary diary) {
        return diaryRepository.save(diary);
    }

    // 본인의 다이어리 목록 조회
    public List<Diary> getDiariesByUser(String username) {
        return diaryRepository.findByUsername(username);
    }

    // 특정 다이어리 조회
    public Diary getDiaryById(Long id, String username) {
        Optional<Diary> diary = diaryRepository.findById(id);
        if (diary.isPresent() && diary.get().getUsername().equals(username)) {
            return diary.get();
        }
        throw new IllegalArgumentException("Access denied or diary not found.");
    }

    // 다이어리 수정
    public Diary updateDiary(Long id, Diary updatedDiary, String username) {
        Diary diary = getDiaryById(id, username);
        diary.setTitle(updatedDiary.getTitle());
        diary.setContent(updatedDiary.getContent());
        return diaryRepository.save(diary);
    }

    // 다이어리 삭제
    public void deleteDiary(Long id, String username) {
        Diary diary = getDiaryById(id, username);
        diaryRepository.delete(diary);
    }
}
