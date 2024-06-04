package org.example.cafeflow.chat.service;

import lombok.RequiredArgsConstructor;
import org.example.cafeflow.Member.domain.Member;
import org.example.cafeflow.Member.repository.MemberRepository;
import org.example.cafeflow.chat.domain.ChatRoom;
import org.example.cafeflow.chat.dto.ChatRoomDto;
import org.example.cafeflow.chat.repository.ChatRoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    public ChatRoomDto createRoom(Long cafeOwnerId, Long userId) {
        Member cafeOwner = memberRepository.findById(cafeOwnerId)
                .orElseThrow(() -> new IllegalArgumentException("Cafe owner not found"));
        Member user = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setCafeOwner(cafeOwner);
        chatRoom.setUser(user);
        chatRoom = chatRoomRepository.save(chatRoom);

        return convertToDto(chatRoom);
    }

    public void deleteRoom(Long roomId) {
        chatRoomRepository.deleteById(roomId);
    }

    public ChatRoomDto getRoom(Long roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("ChatRoom not found"));
        return convertToDto(chatRoom);
    }

    private ChatRoomDto convertToDto(ChatRoom chatRoom) {
        ChatRoomDto chatRoomDto = new ChatRoomDto();
        chatRoomDto.setId(chatRoom.getId());
        chatRoomDto.setCafeOwnerId(chatRoom.getCafeOwner().getId());
        chatRoomDto.setCafeOwnerUsername(chatRoom.getCafeOwner().getUsername());
        chatRoomDto.setUserId(chatRoom.getUser().getId());
        chatRoomDto.setUserUsername(chatRoom.getUser().getUsername());
        return chatRoomDto;
    }
}
