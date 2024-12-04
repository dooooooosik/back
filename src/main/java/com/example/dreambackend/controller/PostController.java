package com.example.dreambackend.controller;

import com.example.dreambackend.entity.Post;
import com.example.dreambackend.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5500")
@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Post> createPost(Authentication authentication, @RequestBody PostRequest request) {
        // Authentication에서 사용자 ID를 가져옵니다.
        Long userId = Long.valueOf(authentication.getName());
        Post post = postService.createPost(userId, request.getTitle(), request.getContent());
        return ResponseEntity.ok(post);
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(Authentication authentication, @PathVariable Long id) {
        // 사용자 ID를 가져와 삭제 요청에 사용합니다.
        Long userId = Long.valueOf(authentication.getName());
        postService.deletePost(id, userId);
        return ResponseEntity.noContent().build();
    }
}

class PostRequest {
    private String title;
    private String content;

    public PostRequest() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

class PostResponse {
    private String content;
    private String title;
    private String authorName;


    public PostResponse(Post post) {
        this.content = post.getContent();
        this.title = post.getTitle();
        this.authorName = post.getAuthor().getUsername();
    }
}
