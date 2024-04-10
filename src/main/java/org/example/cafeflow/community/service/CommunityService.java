package org.example.cafeflow.community.service;

import jakarta.transaction.Transactional;
import org.example.cafeflow.community.domain.*;
import org.example.cafeflow.community.dto.CommentCreationDto;
import org.example.cafeflow.community.dto.FriendRequestDto;
import org.example.cafeflow.community.dto.PostCreationDto;
import org.example.cafeflow.community.repository.BoardRepository;
import org.example.cafeflow.community.repository.CommentRepository;
import org.example.cafeflow.community.repository.CommunityMemberRepository;
import org.example.cafeflow.community.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommunityService {

    @Autowired
    private CommunityMemberRepository communityMemberRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public String addFriend(FriendRequestDto request) {
        CommunityMember member = communityMemberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));
        CommunityMember friend = communityMemberRepository.findById(request.getFriendId())
                .orElseThrow(() -> new RuntimeException("친구를 찾을 수 없습니다."));

        Friendship friendship = new Friendship();
        friendship.setMember(member);
        friendship.setFriend(friend);
        member.getFriends().add(friendship);
        friend.getFriends().add(friendship); // 양방향 관계 설정

        communityMemberRepository.save(member);
        communityMemberRepository.save(friend);
        return "친구 추가 완료";
    }

    @Transactional
    public Post createPost(PostCreationDto dto) {
        CommunityMember author = communityMemberRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("작성자를 찾을 수 없습니다."));
        Board board = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new RuntimeException("보드를 찾을 수 없습니다."));

        Post post = new Post();
        post.setAuthor(author);
        post.setBoard(board);
        post.setContent(dto.getContent());
        post.setCreatedAt(LocalDateTime.now());
        postRepository.save(post);
        return post;
    }

    @Transactional
    public Comment addComment(CommentCreationDto dto) {
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        CommunityMember author = communityMemberRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("작성자를 찾을 수 없습니다."));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setAuthor(author);
        comment.setContent(dto.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(comment);
        return comment;
    }
}