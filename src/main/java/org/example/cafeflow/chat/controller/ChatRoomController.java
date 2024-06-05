package org.example.cafeflow.chat.controller;

import org.example.cafeflow.chat.dto.ChatRoomDto;
import org.example.cafeflow.chat.dto.ChatRoomRequest;
import org.example.cafeflow.chat.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat/rooms")
public class ChatRoomController {
    @Autowired
    private ChatRoomService chatRoomService;

    @PostMapping
    public ChatRoomDto createRoom(@RequestBody ChatRoomRequest request) {
        return chatRoomService.createRoom(request.getCafeOwnerId(), request.getUserId());
    }

    @DeleteMapping("/{roomId}")
    public void deleteRoom(@PathVariable("roomId") Long roomId) {
        chatRoomService.deleteRoom(roomId);
    }

    @GetMapping("/{roomId}")
    public ChatRoomDto getRoom(@PathVariable("roomId") Long roomId) {
        return chatRoomService.getRoom(roomId);
    }



    @GetMapping("/cafeOwner/{cafeOwnerId}")
    public List<ChatRoomDto> getRoomsByCafeOwnerId(@PathVariable("cafeOwnerId") Long cafeOwnerId) {
        return chatRoomService.getRoomsByCafeOwnerId(cafeOwnerId);
    }

    @GetMapping("/user/{userId}")
    public List<ChatRoomDto> getRoomsByUserId(@PathVariable("userId") Long userId) {
        return chatRoomService.getRoomsByUserId(userId);
    }
}
