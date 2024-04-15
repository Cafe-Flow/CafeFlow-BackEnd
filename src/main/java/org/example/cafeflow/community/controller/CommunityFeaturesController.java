package org.example.cafeflow.community.controller;

import org.example.cafeflow.community.dto.MeetingDto;
import org.example.cafeflow.community.dto.PlanSharingDto;
import org.example.cafeflow.community.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
