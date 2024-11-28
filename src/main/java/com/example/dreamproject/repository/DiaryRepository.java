package com.example.dreamproject.repository;

import com.example.dreamproject.entity.Diary;
import com.example.dreamproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByUser(User user);
}
