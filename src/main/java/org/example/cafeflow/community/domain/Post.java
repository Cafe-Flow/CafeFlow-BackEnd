package org.example.cafeflow.community.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private CommunityMember author;

    private String content;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    private String imageUrl;
    private String region;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;


    public Post(CommunityMember author, String content, LocalDateTime createdAt, Board board, String imageUrl, String region) {
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
        this.board = board;
        this.imageUrl = imageUrl;
        this.region = region;
    }
}
