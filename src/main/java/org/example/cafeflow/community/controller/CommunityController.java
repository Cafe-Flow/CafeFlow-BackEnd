package org.example.cafeflow.community.controller;

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

import java.util.List;

@RestController
@RequestMapping("/api/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

//    @PostMapping("/posts")
//    public ResponseEntity<PostDto> createPost(@RequestParam("boardId") Long boardId,
//                                              @RequestParam("title") String title,
//                                              @RequestParam("content") String content,
//                                              @RequestParam("stateId") Long stateId,
//                                              @RequestParam("image") MultipartFile image) {
//        PostCreationDto creationDto = new PostCreationDto(boardId, title, content, image, stateId);
//        PostDto postDto = communityService.createPost(creationDto);
//        return new ResponseEntity<>(postDto, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/posts/{id}")
//    public ResponseEntity<PostDto> updatePost(@PathVariable Long id,
//                                              @RequestParam("title") String title,
//                                              @RequestParam("content") String content,
//                                              @RequestParam("stateId") Long stateId,
//                                              @RequestParam("image") MultipartFile image) {
//        PostUpdateDto updateDto = new PostUpdateDto(title, content, image, stateId);
//        PostDto postDto = communityService.updatePost(id, updateDto);
//        return ResponseEntity.ok(postDto);
//    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        communityService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> posts = communityService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

//    @GetMapping("/posts/{id}")
//    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
//        PostDto postDto = communityService.getPostById(id);
//        return ResponseEntity.ok(postDto);
//    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> addCommentToPost(@PathVariable Long postId,
                                                       @RequestParam("content") String content) {
        CommentCreationDto creationDto = new CommentCreationDto(postId, content);
        CommentDto commentDto = communityService.createComment(creationDto);
        return new ResponseEntity<>(commentDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        communityService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentDto> comments = communityService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }
}
