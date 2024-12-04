package com.example.dreambackend.service;

import com.example.dreambackend.entity.Post;
import com.example.dreambackend.entity.AppUser;
import com.example.dreambackend.repository.PostRepository;
import com.example.dreambackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // 게시글 생성
    public Post createPost(Long userId, String title, String content) {
        // 사용자 확인
        AppUser user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        // 게시글 생성 및 저장
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setAuthor(user);
        return postRepository.save(post);
    }

    // 모든 게시글 조회
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // ID로 게시글 조회
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + id));
    }

    // 게시글 수정
    public Post updatePost(Long userId, Long postId, String title, String content) {
        // 게시글 조회
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId));

        // 권한 확인 (작성자와 요청 사용자 ID 비교)
        if (!post.getAuthor().getId().equals(userId)) {
            throw new IllegalStateException("You are not the author of this post");
        }

        // 게시글 내용 수정
        post.setTitle(title);
        post.setContent(content);
        return postRepository.save(post);
    }

    // 게시글 삭제
    public void deletePost(Long postId, Long userId) {
        // 게시글 조회
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId));

        // 권한 확인 (작성자와 요청 사용자 ID 비교)
        if (!post.getAuthor().getId().equals(userId)) {
            throw new IllegalStateException("You are not the author of this post");
        }

        // 게시글 삭제
        postRepository.delete(post);
    }
}
