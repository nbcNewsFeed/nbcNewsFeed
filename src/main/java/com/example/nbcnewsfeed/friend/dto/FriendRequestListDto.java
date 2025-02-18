package com.example.nbcnewsfeed.friend.dto;

import com.example.nbcnewsfeed.friend.entity.FriendStatus;
import lombok.Getter;

@Getter
public class FriendRequestListDto { //친구 요청 목록 반환 dto

    private final Long user1Id;
    private final Long user2Id;
    private final FriendStatus friendStatus;

    public FriendRequestListDto(Long user1Id, Long user2Id, FriendStatus friendStatus) {
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.friendStatus = friendStatus;
    }
}
