package org.example.cafeflow.chat.service;

import lombok.RequiredArgsConstructor;
import org.example.cafeflow.Member.domain.Member;
import org.example.cafeflow.Member.repository.MemberRepository;
import org.example.cafeflow.chat.domain.ChatRoom;
import org.example.cafeflow.chat.dto.ChatRoomDto;
import org.example.cafeflow.chat.repository.ChatRoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        // 기존의 채팅방이 있는지 확인
        Optional<ChatRoom> existingChatRoom = chatRoomRepository.findByCafeOwnerIdAndUserId(cafeOwnerId, userId);
        if (existingChatRoom.isPresent()) {
            return convertToDto(existingChatRoom.get());
        }

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

    public List<ChatRoomDto> getRoomsByCafeOwnerId(Long cafeOwnerId) {
        return chatRoomRepository.findByCafeOwnerId(cafeOwnerId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ChatRoomDto> getRoomsByUserId(Long userId) {
        return chatRoomRepository.findByUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
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
