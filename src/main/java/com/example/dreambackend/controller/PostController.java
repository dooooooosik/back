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
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(
            Authentication authentication,
            @PathVariable Long id,
            @RequestBody PostRequest request
    ) {
        Long userId = Long.valueOf(authentication.getName()); // 로그인한 사용자 ID 가져오기
        Post updatedPost = postService.updatePost(userId, id, request.getTitle(), request.getContent()); // 서비스 호출
        return ResponseEntity.ok(updatedPost); // 수정된 게시글 반환
    }
    @GetMapping("/myposts")
    public ResponseEntity<List<Post>> getMyPosts(Authentication authentication) {
        // JWT에서 사용자 ID 추출
        Long userId = extractUserIdFromAuthentication(authentication);
        List<Post> posts = postService.getPostsByUserId(userId);
        return ResponseEntity.ok(posts);
    }

    private Long extractUserIdFromAuthentication(Authentication authentication) {
        // JWT의 클레임에서 사용자 ID를 추출하는 로직
        return Long.valueOf(authentication.getName());
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
    private String username;


    public PostResponse(Post post) {
        this.content = post.getContent();
        this.title = post.getTitle();
        this.username = post.getUser().getUsername();
    }
}
