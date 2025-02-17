package com.example.nbcnewsfeed.friend.entity;

import com.example.nbcnewsfeed.BaseEntity;
import com.example.nbcnewsfeed.user.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "friend_request")
public class FriendRequest extends BaseEntity {

    //친구 요청 대기 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="sender_id", nullable=false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="receiver_id", nullable=false)
    private User receiver;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private FriendStatus friendStatus;


    public FriendRequest(User sender, User receiver, FriendStatus friendStatus) {
        this.sender = sender;
        this.receiver = receiver;
        this.friendStatus = friendStatus;
    }

    public FriendRequest() {

    }
}
