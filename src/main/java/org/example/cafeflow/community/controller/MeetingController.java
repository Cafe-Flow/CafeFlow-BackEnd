package org.example.cafeflow.community.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MeetingController {

    @MessageMapping("/meet/create")
    @SendTo("/topic/meets")
    public String createMeeting(String details) {
        return "새 모임이 생성되었습니다: " + details;
    }
}
