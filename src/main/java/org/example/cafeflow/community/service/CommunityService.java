package org.example.cafeflow.community.service;

import org.example.cafeflow.Member.domain.Member;
import org.example.cafeflow.Member.domain.State;
import org.example.cafeflow.Member.repository.MemberRepository;
import org.example.cafeflow.Member.repository.StateRepository;
import org.example.cafeflow.community.domain.Board;
import org.example.cafeflow.community.domain.Comment;
import org.example.cafeflow.community.domain.Post;
import org.example.cafeflow.community.dto.*;
import org.example.cafeflow.community.repository.BoardRepository;
import org.example.cafeflow.community.repository.CommentRepository;
import org.example.cafeflow.community.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public PostDto createPost(PostCreationDto creationDto, MultipartFile image) throws IOException {
        Member member = memberRepository.findById(creationDto.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        State state = stateRepository.findById(creationDto.getStateId())
                .orElseThrow(() -> new IllegalArgumentException("지역을 찾을 수 없습니다."));
        Board board = boardRepository.findById(creationDto.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("게시판을 찾을 수 없습니다."));

        Post post = new Post();
        post.setTitle(creationDto.getTitle());
        post.setContent(creationDto.getContent());
        post.setState(state);
        post.setAuthor(member);
        post.setBoard(board);
        if (image != null && !image.isEmpty()) {
            post.setImage(image.getBytes());
        }

        postRepository.save(post);
        return convertPostToDto(post);
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

        postRepository.save(post);
        return convertPostToDto(post);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        if (post.getBoard() != null) {
            Board board = post.getBoard();
            board.getPosts().remove(post);
            boardRepository.save(board);
        }

        commentRepository.deleteAll(post.getComments());
        post.getLikedBy().clear();

        postRepository.delete(post);
    }


    @Transactional(readOnly = true)
    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::convertPostToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto createComment(CommentCreationDto creationDto) {
        Post post = postRepository.findById(creationDto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        Member member = memberRepository.findById(creationDto.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setAuthor(member);
        comment.setContent(creationDto.getContent());

        if (creationDto.getParentCommentId() != null) {
            Comment parentComment = commentRepository.findById(creationDto.getParentCommentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글을 찾을 수 없습니다."));
            comment.setParentComment(parentComment);
        }

        commentRepository.save(comment);
        return convertCommentToDto(comment);
    }

    @Transactional
    public CommentDto createReply(Long parentCommentId, CommentCreationDto creationDto) {
        Comment parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new IllegalArgumentException("부모 댓글을 찾을 수 없습니다."));
        Member member = memberRepository.findById(creationDto.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Comment reply = new Comment();
        reply.setPost(parentComment.getPost());
        reply.setAuthor(member);
        reply.setContent(creationDto.getContent());
        reply.setParentComment(parentComment);

        commentRepository.save(reply);
        return convertCommentToDto(reply);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        commentRepository.delete(comment);
    }

    private PostDto convertPostToDto(Post post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setBoardId(post.getBoard().getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setImage(post.getImage());
        dto.setAuthorNickname(post.getAuthor().getNickname());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        dto.setStateId(post.getState().getId());
        dto.setStateName(post.getState().getName());
        dto.setLikesCount(post.getLikedBy().size());
        dto.setLikedByCurrentUser(isCurrentUserLiked(post.getId()));
        dto.setCommentCount(post.getComments().size());
        dto.setViews(post.getViews());
        dto.setComments(post.getComments().stream().map(this::convertCommentToDto).collect(Collectors.toList()));
        return dto;
    }

    private CommentDto convertCommentToDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setPostId(comment.getPost().getId());
        dto.setAuthorNickname(comment.getAuthor().getNickname());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUpdatedAt(comment.getUpdatedAt());
        dto.setParentCommentId(comment.getParentComment() != null ? comment.getParentComment().getId() : null);
        dto.setReplies(comment.getReplies().stream().map(this::convertCommentToDto).collect(Collectors.toList()));
        return dto;
    }

    private boolean isCurrentUserLiked(Long postId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Member member = memberRepository.findByLoginId(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        return post.getLikedBy().contains(member);
    }

    @Transactional(readOnly = true)
    public PostDto getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        post.incrementViews();
        postRepository.save(post);
        return convertPostToDto(post);
    }

    @Transactional
    public void toggleLike(Long postId, Long memberId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        if (!post.addLike(member)) {
            post.removeLike(member);
        }
        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public List<PostDto> getPostsByKeyword(String keyword) {
        return postRepository.searchByKeyword(keyword).stream().map(this::convertPostToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostDto> getPostsByTitle(String title) {
        return postRepository.findByTitleContaining(title).stream().map(this::convertPostToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostDto> getPostsByAuthorNickname(String nickname) {
        return postRepository.findByAuthorNickname(nickname).stream().map(this::convertPostToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostDto> getPostsByStateName(String stateName) {
        return postRepository.findByStateName(stateName).stream().map(this::convertPostToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId).stream().map(this::convertCommentToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByAuthorNickname(String nickname) {
        return commentRepository.findByAuthorNickname(nickname).stream().map(this::convertCommentToDto).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<PostDto> getPostsByAuthorId(Long authorId) {
        return postRepository.findByAuthorId(authorId).stream().map(this::convertPostToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostDto> getPostsByStateId(Long stateId) {
        return postRepository.findByStateId(stateId).stream().map(this::convertPostToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByAuthorId(Long authorId) {
        return commentRepository.findByAuthorId(authorId).stream().map(this::convertCommentToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostDto> getPostsByBoardId(Long boardId) {
        return postRepository.findByBoardId(boardId).stream()
                .map(this::convertPostToDto)
                .collect(Collectors.toList());
    }
}
