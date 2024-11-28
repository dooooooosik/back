package com.example.dreamproject.repository;

import com.example.dreamproject.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    // 작성자의 다이어리 목록만 조회
    List<Diary> findByUsername(String username);
}
