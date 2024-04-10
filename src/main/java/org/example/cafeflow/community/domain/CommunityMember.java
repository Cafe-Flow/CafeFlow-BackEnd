package org.example.cafeflow.community.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.cafeflow.Member.domain.Member;

import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "member_id")
public class CommunityMember extends Member {

    @OneToMany(mappedBy = "member")
    private Set<Friendship> friends;

    @OneToMany(mappedBy = "author")
    private Set<Post> posts;
}