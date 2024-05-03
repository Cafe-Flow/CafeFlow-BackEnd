package org.example.cafeflow.community.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.example.cafeflow.Member.domain.Member;
import org.example.cafeflow.Member.repository.MemberRepository;
import org.example.cafeflow.Member.util.JwtTokenProvider;
import org.example.cafeflow.Member.util.UserPrincipal;
import org.example.cafeflow.community.dto.*;
import org.example.cafeflow.community.service.CommunityService;
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
    private final MemberRepository memberRepository;

    private final JwtTokenProvider jwtTokenProvider;
    public CommunityController(CommunityService communityService, JwtTokenProvider jwtTokenProvider,MemberRepository memberRepository) {
        this.communityService = communityService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberRepository = memberRepository;
    }
    private UserPrincipal getCurrentUser(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            String username = jwtTokenProvider.getUsername(token);
            Member member = memberRepository.findByLoginId(username)
                    .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
            return new UserPrincipal(member);
        }
        return null;
    }

    @PostMapping(value = "/posts", consumes = {"multipart/form-data"})
    public ResponseEntity<?> createPost(HttpServletRequest request,
                                        @RequestParam("boardId") Long boardId,
                                        @RequestParam("title") String title,
                                        @RequestParam("content") String content,
                                        @RequestParam("stateId") Long stateId,
                                        @RequestParam(value = "image", required = false) MultipartFile image) {

        UserPrincipal currentUser = getCurrentUser(request);
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        PostCreationDto creationDto = new PostCreationDto(boardId, currentUser.getId(), currentUser.getNickname(), title, content, image, stateId);
        try {
            PostDto postDto = communityService.createPost(creationDto,image);
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

    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<Void> toggleLike(@PathVariable("postId") Long postId, HttpServletRequest request) {
        UserPrincipal currentUser = getCurrentUser(request);
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        communityService.toggleLike(postId, currentUser.getId());
        return ResponseEntity.ok().build();
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
    public ResponseEntity<CommentDto> addCommentToPost(@PathVariable("postId") Long postId, @RequestBody CommentCreationDto creationDto, HttpServletRequest request) {
        UserPrincipal currentUser = getCurrentUser(request);
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        creationDto.setPostId(postId);
        creationDto.setAuthorId(currentUser.getId());
        CommentDto commentDto = communityService.createComment(creationDto);
        return new ResponseEntity<>(commentDto, HttpStatus.CREATED);
    }

    @PostMapping("/posts/{postId}/comments/{commentId}/replies")
    public ResponseEntity<CommentDto> addReplyToComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId, @RequestBody CommentCreationDto creationDto, HttpServletRequest request) {
        UserPrincipal currentUser = getCurrentUser(request);
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        creationDto.setPostId(postId);
        creationDto.setParentCommentId(commentId);
        creationDto.setAuthorId(currentUser.getId());
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

    @GetMapping("/posts/author/{authorId}")
    public ResponseEntity<List<PostDto>> getPostsByAuthorId(@PathVariable("authorId") Long authorId) {
        List<PostDto> posts = communityService.getPostsByAuthorId(authorId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/state/{stateId}")
    public ResponseEntity<List<PostDto>> getPostsByStateId(@PathVariable("stateId") Long stateId) {
        List<PostDto> posts = communityService.getPostsByStateId(stateId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/comments/author/{authorId}")
    public ResponseEntity<List<CommentDto>> getCommentsByAuthorId(@PathVariable("authorId") Long authorId) {
        List<CommentDto> comments = communityService.getCommentsByAuthorId(authorId);
        return ResponseEntity.ok(comments);
    }
    @GetMapping("/posts/search")
    public ResponseEntity<List<PostDto>> getPostsByKeyword(@RequestParam("keyword") String keyword) {
        List<PostDto> posts = communityService.getPostsByKeyword(keyword);
        return ResponseEntity.ok(posts);
    }
    @GetMapping("/posts/title")
    public ResponseEntity<List<PostDto>> getPostsByTitle(@RequestParam("title") String title) {
        List<PostDto> posts = communityService.getPostsByTitle(title);
        return ResponseEntity.ok(posts);
    }
    @GetMapping("/posts/author/nickname")
    public ResponseEntity<List<PostDto>> getPostsByAuthorNickname(@RequestParam("nickname") String nickname) {
        List<PostDto> posts = communityService.getPostsByAuthorNickname(nickname);
        return ResponseEntity.ok(posts);
    }
    @GetMapping("/posts/state/name")
    public ResponseEntity<List<PostDto>> getPostsByStateName(@RequestParam("stateName") String stateName) {
        List<PostDto> posts = communityService.getPostsByStateName(stateName);
        return ResponseEntity.ok(posts);
    }
    @GetMapping("/comments/author/nickname")
    public ResponseEntity<List<CommentDto>> getCommentsByAuthorUsername(@RequestParam("nickname") String nickname) {
        List<CommentDto> comments = communityService.getCommentsByAuthorNickname(nickname);
        return ResponseEntity.ok(comments);
    }

}
