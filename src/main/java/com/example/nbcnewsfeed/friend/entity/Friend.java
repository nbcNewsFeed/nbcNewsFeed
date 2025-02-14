package com.example.nbcnewsfeed.friend.entity;

import com.example.nbcnewsfeed.BaseEntity;
import com.example.nbcnewsfeed.user.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "friend")
public class Friend extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="sender_id", nullable=false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="receiver_id", nullable=false)
    private User receiver;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private FriendStatus status;


    public Friend(User sender, User receiver, FriendStatus status) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = status;
    }

    public Friend() {

    }
}
