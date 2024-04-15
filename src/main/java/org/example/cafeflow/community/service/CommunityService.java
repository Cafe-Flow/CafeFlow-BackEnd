package org.example.cafeflow.community.service;

import jakarta.transaction.Transactional;
import org.example.cafeflow.community.domain.*;
import org.example.cafeflow.community.dto.*;
import org.example.cafeflow.community.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private FileStorageService fileStorageService;

    @Transactional
    public String addFriend(FriendRequestDto request) {
        CommunityMember member = communityMemberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        CommunityMember friend = communityMemberRepository.findById(request.getFriendId())
                .orElseThrow(() -> new IllegalArgumentException("친구를 찾을 수 없습니다."));
        if (member.getFriends().stream().anyMatch(f -> f.getFriend().getId().equals(friend.getId()))) {
            throw new IllegalStateException("이미 친구입니다.");
        }

        Friendship friendship = new Friendship();
        friendship.setMember(member);
        friendship.setFriend(friend);
        friendship.setCreatedAt(LocalDateTime.now());
        member.getFriends().add(friendship);
        friend.getFriends().add(friendship);

        communityMemberRepository.save(member);
        communityMemberRepository.save(friend);
        return "친구 추가 완료";
    }

    @Transactional
    public Post createPost(PostCreationDto dto) {
        CommunityMember author = communityMemberRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("작성자를 찾을 수 없습니다."));
        Board board = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("보드를 찾을 수 없습니다."));

        Post post = new Post(author, dto.getContent(), LocalDateTime.now(), board, dto.getImageUrl());
        postRepository.save(post);
        return post;
    }

    @Transactional
    public Post updatePost(Long postId, PostUpdateDto dto, MultipartFile image) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        if (image != null && !image.isEmpty()) {
            String imageUrl = fileStorageService.storeFile(image);
            post.setImageUrl(imageUrl);
        }
        post.setContent(dto.getContent());
        post.setCreatedAt(LocalDateTime.now());
        postRepository.save(post);
        return post;
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        postRepository.delete(post);
    }

    @Transactional
    public Comment addComment(CommentCreationDto dto) {
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        CommunityMember author = communityMemberRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("작성자를 찾을 수 없습니다."));

        Comment comment = new Comment(null, post, author, dto.getContent(), LocalDateTime.now());
        commentRepository.save(comment);
        return comment;
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        commentRepository.delete(comment);
    }

    @Transactional
    public String organizeMeeting(MeetingDto meetingDto) {
        Board board = boardRepository.findById(meetingDto.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("보드를 찾을 수 없습니다."));

        Meeting meeting = new Meeting();
        meeting.setBoard(board);
        meeting.setTopic(meetingDto.getTopic());
        meeting.setTime(meetingDto.getTime());
        meetingRepository.save(meeting);
        return "모임 '" + meetingDto.getTopic() + "'이(가) 생성되었습니다.";
    }

    @Transactional
    public String sharePlan(PlanSharingDto planDto) {
        CommunityMember user = communityMemberRepository.findById(planDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Plan plan = new Plan();
        plan.setUser(user);
        plan.setDetails(planDto.getDetails());
        plan.setVisitTime(planDto.getVisitTime());
        planRepository.save(plan);
        return "방문 계획 '" + planDto.getDetails() + "'이(가) 공유되었습니다.";
    }

    @Transactional
    public MeetingDto getMeeting(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new IllegalArgumentException("모임을 찾을 수 없습니다."));
        return new MeetingDto(meeting.getBoard().getId(), meeting.getTopic(), meeting.getTime());
    }

    @Transactional
    public PlanDto getPlan(Long planId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("계획을 찾을 수 없습니다."));
        return new PlanDto(plan.getUser().getId(), plan.getDetails(), plan.getVisitTime());
    }

    @Transactional
    public String deleteMeeting(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new IllegalArgumentException("모임을 찾을 수 없습니다."));
        meetingRepository.delete(meeting);
        return "모임이 성공적으로 삭제되었습니다!";
    }

    @Transactional
    public String deletePlan(Long planId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("계획을 찾을 수 없습니다."));
        planRepository.delete(plan);
        return "계획이 성공적으로 삭제되었습니다!";
    }


}