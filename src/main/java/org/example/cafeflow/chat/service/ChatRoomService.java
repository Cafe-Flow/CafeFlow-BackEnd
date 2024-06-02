package org.example.cafeflow.chat.service;

import lombok.RequiredArgsConstructor;
import org.example.cafeflow.Member.domain.Member;
import org.example.cafeflow.Member.repository.MemberRepository;
import org.example.cafeflow.chat.domain.ChatRoom;
import org.example.cafeflow.chat.repository.ChatRoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    public ChatRoom createRoom(Long cafeOwnerId, Long userId) {
        Member cafeOwner = memberRepository.findById(cafeOwnerId)
                .orElseThrow(() -> new IllegalArgumentException("Cafe owner not found"));
        Member user = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setCafeOwner(cafeOwner);
        chatRoom.setUser(user);
        return chatRoomRepository.save(chatRoom);
    }

    public void deleteRoom(Long roomId) {
        chatRoomRepository.deleteById(roomId);
    }

    public ChatRoom getRoom(Long roomId) {
        return chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("ChatRoom not found"));
    }
}
