package com.example.nbcnewsfeed.friend.entity;

import com.example.nbcnewsfeed.BaseEntity;
import com.example.nbcnewsfeed.user.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name="friendship")
public class Friendship extends BaseEntity {

    //진짜 친구 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="user1_id", nullable = false)
    private User user1;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="user2_id", nullable = false)
    private User user2;

    public Friendship(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    public Friendship() {

    }
}
