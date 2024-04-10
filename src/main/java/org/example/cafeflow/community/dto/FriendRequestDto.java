package org.example.cafeflow.community.dto;


import lombok.Data;

@Data
public class FriendRequestDto {
    private Long memberId;
    private Long friendId;
}