package org.example.cafeflow.chat.service;

import lombok.RequiredArgsConstructor;
import org.example.cafeflow.Member.domain.Member;
import org.example.cafeflow.Member.repository.MemberRepository;
import org.example.cafeflow.chat.domain.ChatMessage;
import org.example.cafeflow.chat.domain.ChatRoom;
import org.example.cafeflow.chat.dto.ChatMessageDto;
import org.example.cafeflow.chat.repository.ChatMessageRepository;
import org.example.cafeflow.chat.repository.ChatRoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;

    public void saveMessage(ChatMessageDto chatMessageDto) {
        Member sender = memberRepository.findById(chatMessageDto.getSenderId()).orElseThrow(() -> new IllegalArgumentException("Sender not found"));
        Member receiver = memberRepository.findById(chatMessageDto.getReceiverId()).orElseThrow(() -> new IllegalArgumentException("Receiver not found"));
        ChatRoom chatRoom = chatRoomRepository.findById(chatMessageDto.getChatRoomId()).orElseThrow(() -> new IllegalArgumentException("Chat room not found"));

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(sender);
        chatMessage.setReceiver(receiver);
        chatMessage.setChatRoom(chatRoom);
        chatMessage.setContent(chatMessageDto.getContent());
        chatMessage.setTimestamp(LocalDateTime.now());
        chatMessage.setReadStatus(false);
        chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessageDto> getChatHistory(Long roomId) {
        return chatMessageRepository.findByChatRoomIdOrderByTimestampAsc(roomId)
                .stream()
                .map(msg -> new ChatMessageDto(msg.getSender().getId(), msg.getReceiver().getId(), msg.getChatRoom().getId(), msg.getContent()))
                .collect(Collectors.toList());
    }

    public List<ChatMessageDto> getUnreadMessages(Long roomId) {
        return chatMessageRepository.findByChatRoomIdOrderByTimestampAsc(roomId)
                .stream()
                .filter(msg -> !msg.isReadStatus())
                .map(msg -> new ChatMessageDto(msg.getSender().getId(), msg.getReceiver().getId(), msg.getChatRoom().getId(), msg.getContent()))
                .collect(Collectors.toList());
    }

    public void updateReadStatus(List<Long> messageIds) {
        List<ChatMessage> messages = chatMessageRepository.findAllById(messageIds);
        messages.forEach(message -> message.setReadStatus(true));
        chatMessageRepository.saveAll(messages);
    }
}
