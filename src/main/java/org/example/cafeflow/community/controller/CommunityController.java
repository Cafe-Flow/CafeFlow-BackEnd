package org.example.cafeflow.community.controller;

import org.example.cafeflow.community.domain.Comment;
import org.example.cafeflow.community.domain.Post;
import org.example.cafeflow.community.dto.CommentCreationDto;
import org.example.cafeflow.community.dto.FriendRequestDto;
import org.example.cafeflow.community.dto.PostCreationDto;
import org.example.cafeflow.community.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @PostMapping("/friends/add")
    public ResponseEntity<String> addFriend(@RequestBody FriendRequestDto request) {
        String response = communityService.addFriend(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(@RequestBody PostCreationDto dto) {
        Post post = communityService.createPost(dto);
        return ResponseEntity.ok(post);
    }

    @PostMapping("/comments")
    public ResponseEntity<Comment> addComment(@RequestBody CommentCreationDto dto) {
        Comment comment = communityService.addComment(dto);
        return ResponseEntity.ok(comment);
    }
}