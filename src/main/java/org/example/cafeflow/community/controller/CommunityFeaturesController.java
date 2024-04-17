package org.example.cafeflow.community.controller;

import org.example.cafeflow.community.domain.Meeting;
import org.example.cafeflow.community.domain.Plan;
import org.example.cafeflow.community.domain.Post;
import org.example.cafeflow.community.dto.MeetingDto;
import org.example.cafeflow.community.dto.PlanSharingDto;
import org.example.cafeflow.community.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/community")
public class CommunityFeaturesController {

    @Autowired
    private CommunityService communityService;

    @PostMapping("/meetings/organize")
    public ResponseEntity<String> organizeMeeting(@RequestBody MeetingDto meetingDto) {
        return ResponseEntity.ok(communityService.organizeMeeting(meetingDto));
    }

    @PostMapping("/plans/share")
    public ResponseEntity<String> sharePlan(@RequestBody PlanSharingDto planDto) {
        return ResponseEntity.ok(communityService.sharePlan(planDto));
    }

    @GetMapping("/meetings/board/{boardId}")
    public ResponseEntity<List<Meeting>> getMeetingsByBoard(@PathVariable Long boardId) {
        List<Meeting> meetings = communityService.getAllMeetingsByBoard(boardId);
        return ResponseEntity.ok(meetings);
    }

    @GetMapping("/plans/user/{userId}")
    public ResponseEntity<List<Plan>> getPlansByUser(@PathVariable Long userId) {
        List<Plan> plans = communityService.getAllPlansByUser(userId);
        return ResponseEntity.ok(plans);
    }

    @GetMapping("/posts/board/{boardName}")
    public ResponseEntity<List<Post>> getPostsByBoardName(@PathVariable String boardName) {
        List<Post> posts = communityService.getPostsByBoardName(boardName);
        return ResponseEntity.ok(posts);
    }
}