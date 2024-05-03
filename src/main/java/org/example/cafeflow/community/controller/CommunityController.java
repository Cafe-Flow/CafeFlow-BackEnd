package org.example.cafeflow.community.controller;

import jakarta.validation.Valid;
import org.example.cafeflow.community.dto.CommentCreationDto;
import org.example.cafeflow.community.dto.CommentDto;
import org.example.cafeflow.community.dto.PostCreationDto;
import org.example.cafeflow.community.dto.PostDto;
import org.example.cafeflow.community.dto.PostUpdateDto;
import org.example.cafeflow.community.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/community")
public class CommunityController {

    private final CommunityService communityService;

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @PostMapping("/posts")
    public ResponseEntity<?> createPost(@Valid @ModelAttribute PostCreationDto creationDto) {
        try {
            PostDto postDto = communityService.createPost(creationDto);
            return new ResponseEntity<>(postDto, HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 업로드 처리 중 오류가 발생했습니다.");
        }
    }


    @PutMapping("/posts/{id}")
    public ResponseEntity<?> updatePost(@PathVariable("id") Long id,
                                        @Valid @ModelAttribute PostUpdateDto updateDto) {
        try {
            PostDto postDto = communityService.updatePost(id, updateDto);
            return ResponseEntity.ok(postDto);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 업로드 처리 중 오류가 발생했습니다.");
        }
    }
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Long id) {
        communityService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> posts = communityService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") Long id) {
        PostDto postDto = communityService.getPostById(id);
        return ResponseEntity.ok(postDto);
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> addCommentToPost(@PathVariable("postId") Long postId, @RequestBody CommentCreationDto creationDto) {
        creationDto.setPostId(postId);
        CommentDto commentDto = communityService.createComment(creationDto);
        return new ResponseEntity<>(commentDto, HttpStatus.CREATED);
    }
    @PostMapping("/posts/{postId}/comments/{commentId}/replies")
    public ResponseEntity<CommentDto> addReplyToComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId, @RequestBody CommentCreationDto creationDto) {
        creationDto.setPostId(postId);
        CommentDto commentDto = communityService.createReply(commentId, creationDto);
        return new ResponseEntity<>(commentDto, HttpStatus.CREATED);
    }



    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable(("id")) Long id) {
        communityService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable("postId") Long postId) {
        List<CommentDto> comments = communityService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }
}
