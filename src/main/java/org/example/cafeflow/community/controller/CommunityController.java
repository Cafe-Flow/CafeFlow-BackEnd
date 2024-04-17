package org.example.cafeflow.community.controller;

import org.example.cafeflow.community.domain.*;
import org.example.cafeflow.community.dto.*;
import org.example.cafeflow.community.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @PostMapping("/friends/add")
    public ResponseEntity<String> addFriend(@RequestBody FriendRequestDto request) {
        return ResponseEntity.ok(communityService.addFriend(request));
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(@RequestBody PostCreationDto dto) {
        Post post = communityService.createPost(dto);
        return ResponseEntity.ok(post);
    }


    @PutMapping("/posts/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody PostUpdateDto dto) {
        return ResponseEntity.ok(communityService.updatePost(postId, dto));
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
    @GetMapping("/posts/{boardId}/{region}")
    public ResponseEntity<List<Post>> getRegionalPosts(@PathVariable Long boardId, @PathVariable String region) {
        List<Post> posts = communityService.getPostsForBoardAndRegion(boardId, region);
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/meetings")
    public ResponseEntity<Meeting> createMeeting(@RequestBody MeetingDto meetingDto, @RequestParam String location) {
        Meeting meeting = communityService.organizeCommunityMeeting(meetingDto, location);
        return ResponseEntity.ok(meeting);
    }

    @GetMapping("/posts/author/{authorId}")
    public ResponseEntity<List<Post>> getPostsByAuthor(@PathVariable Long authorId) {
        List<Post> posts = communityService.getPostsByAuthor(authorId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/board/{boardId}")
    public ResponseEntity<List<Post>> getPostsByBoard(@PathVariable Long boardId) {
        List<Post> posts = communityService.getPostsByBoard(boardId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/search")
    public ResponseEntity<List<Post>> searchPosts(@RequestParam String keyword) {
        List<Post> posts = communityService.searchPostsByContent(keyword);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/members/{memberId}/friends")
    public ResponseEntity<CommunityMember> getMemberWithFriends(@PathVariable Long memberId) {
        return communityService.getMemberWithFriends(memberId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/members/username/{username}")
    public ResponseEntity<CommunityMember> getMemberByUsername(@PathVariable String username) {
        return communityService.getMemberByUsername(username)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/comments/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPost(@PathVariable Long postId) {
        List<Comment> comments = communityService.getCommentsByPost(postId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/boards/author/{authorId}")
    public ResponseEntity<List<Board>> getBoardsByAuthor(@PathVariable Long authorId) {
        List<Board> boards = communityService.getBoardsByAuthor(authorId);
        return ResponseEntity.ok(boards);
    }
}