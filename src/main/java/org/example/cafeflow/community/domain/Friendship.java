package org.example.cafeflow.community.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private CommunityMember member;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private CommunityMember friend;

    private LocalDateTime createdAt = LocalDateTime.now();
}