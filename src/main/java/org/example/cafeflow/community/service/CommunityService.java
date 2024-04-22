package org.example.cafeflow.community.service;

import org.example.cafeflow.Member.domain.Member;
import org.example.cafeflow.Member.domain.State;
import org.example.cafeflow.Member.repository.MemberRepository;
import org.example.cafeflow.Member.repository.StateRepository;
import org.example.cafeflow.community.domain.Comment;
import org.example.cafeflow.community.domain.Post;
import org.example.cafeflow.community.dto.*;
import org.example.cafeflow.community.repository.CommentRepository;
import org.example.cafeflow.community.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommunityService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StateRepository stateRepository;

    @Transactional
    public PostDto createPost(PostCreationDto creationDto) throws IOException {
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        State state = stateRepository.findById(creationDto.getStateId())
                .orElseThrow(() -> new IllegalArgumentException("지역을 찾을 수 없습니다."));

        Post post = new Post();
        post.setTitle(creationDto.getTitle());
        post.setContent(creationDto.getContent());
        post.setState(state);
        post.setAuthor(member);
        if (creationDto.getImage() != null) {
            post.setImage(creationDto.getImage().getBytes());
        }

        post = postRepository.save(post);
        return convertToDto(post);
    }

    @Transactional
    public PostDto updatePost(Long postId, PostUpdateDto updateDto) throws IOException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        post.setTitle(updateDto.getTitle());
        post.setContent(updateDto.getContent());
        if (updateDto.getImage() != null && !updateDto.getImage().isEmpty()) {
            post.setImage(updateDto.getImage().getBytes());
        }
        State state = stateRepository.findById(updateDto.getStateId())
                .orElseThrow(() -> new IllegalArgumentException("지역을 찾을 수 없습니다."));
        post.setState(state);

        post = postRepository.save(post);
        return convertToDto(post);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        postRepository.delete(post);
    }

    @Transactional(readOnly = true)
    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto createComment(CommentCreationDto creationDto) {
        Post post = postRepository.findById(creationDto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        Member member = memberRepository.findById(1L)  // This should be dynamically fetched based on authenticated user
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setAuthor(member);
        comment.setContent(creationDto.getContent());

        comment = commentRepository.save(comment);
        return convertToDto(comment);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        commentRepository.delete(comment);
    }

    private PostDto convertToDto(Post post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setImage(post.getImage());
        dto.setAuthorUsername(post.getAuthor().getUsername());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        dto.setStateId(post.getState().getId());
        dto.setStateName(post.getState().getName());
        return dto;
    }

    private CommentDto convertToDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setPostId(comment.getPost().getId());
        dto.setAuthorUsername(comment.getAuthor().getUsername());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUpdatedAt(comment.getUpdatedAt());
        return dto;
    }

    @Transactional(readOnly = true)
    public List<PostDto> getPostsByKeyword(String keyword) {
        return postRepository.searchByKeyword(keyword).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostDto> getPostsByTitle(String title) {
        return postRepository.findByTitleContaining(title).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostDto> getPostsByAuthorUsername(String username) {
        return postRepository.findByAuthorUsername(username).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostDto> getPostsByStateName(String stateName) {
        return postRepository.findByStateName(stateName).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByAuthorUsername(String username) {
        return commentRepository.findByAuthorUsername(username).stream().map(this::convertToDto).collect(Collectors.toList());
    }
}
