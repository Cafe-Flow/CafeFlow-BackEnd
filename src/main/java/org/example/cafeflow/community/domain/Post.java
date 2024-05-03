package org.example.cafeflow.community.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cafeflow.Member.domain.Member;
import org.example.cafeflow.Member.domain.State;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    private Member author;

    private int views;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Lob
    @Column(name = "image", columnDefinition="LONGBLOB")
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;


    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "post_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private Set<Member> likedBy = new HashSet<>();

    public void incrementViews() {
        this.views++;
    }

    public boolean addLike(Member member) {
        return likedBy.add(member);
    }

    public boolean removeLike(Member member) {
        return likedBy.remove(member);
    }

    public int countLikes() {
        return likedBy.size();
    }
}
