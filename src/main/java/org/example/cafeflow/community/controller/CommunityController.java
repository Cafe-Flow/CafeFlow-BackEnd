package org.example.cafeflow.community.controller;

import org.example.cafeflow.community.domain.Comment;
import org.example.cafeflow.community.domain.Post;
import org.example.cafeflow.community.dto.CommentCreationDto;
import org.example.cafeflow.community.dto.FriendRequestDto;
import org.example.cafeflow.community.dto.PostCreationDto;
import org.example.cafeflow.community.dto.PostUpdateDto;
import org.example.cafeflow.community.service.CommunityService;
import org.example.cafeflow.community.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/friends/add")
    public ResponseEntity<String> addFriend(@RequestBody FriendRequestDto request) {
        return ResponseEntity.ok(communityService.addFriend(request));
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(@RequestParam("authorId") Long authorId,
                                           @RequestParam("boardId") Long boardId,
                                           @RequestParam("content") String content,
                                           @RequestParam("image") MultipartFile image) {
        String imageUrl = fileStorageService.storeFile(image);
        PostCreationDto dto = new PostCreationDto(authorId, boardId, content, imageUrl);
        return ResponseEntity.ok(communityService.createPost(dto));
    }



    @PutMapping("/posts/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId,
                                           @RequestBody PostUpdateDto dto,
                                           @RequestParam(required = false) MultipartFile image) {
        return ResponseEntity.ok(communityService.updatePost(postId, dto, image));
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        communityService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/comments")
    public ResponseEntity<Comment> addComment(@RequestBody CommentCreationDto dto) {
        return ResponseEntity.ok(communityService.addComment(dto));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        communityService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
