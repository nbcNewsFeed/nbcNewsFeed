package com.example.nbcnewsfeed.friend.entity;

import com.example.nbcnewsfeed.common.entity.BaseEntity;
import com.example.nbcnewsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;

@Entity
@Getter
@Table(name = "friend_request")
@Filter(name = "activeFriendRequestFilter",
        condition = "(sender_id IN (SELECT id FROM user WHERE deleted_at IS NULL)) " +
                "AND (receiver_id IN (SELECT id FROM user WHERE deleted_at IS NULL))" )
@FilterDef(name = "activeFriendRequestFilter")
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
    private FriendStatus friendStatus = FriendStatus.WAITING;


    public FriendRequest(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public FriendRequest() {

    }

    public void updateFriendStatus(FriendStatus newStatus) {
        this.friendStatus = newStatus;
    }
}
