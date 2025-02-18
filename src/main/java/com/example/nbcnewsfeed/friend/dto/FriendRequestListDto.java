package com.example.nbcnewsfeed.friend.dto;

import com.example.nbcnewsfeed.friend.entity.FriendStatus;
import lombok.Getter;

@Getter
public class FriendRequestListDto { //친구 요청 목록 반환 dto

    private final Long senderId;
    private final Long receiverId;
    private final FriendStatus friendStatus;


    public FriendRequestListDto(Long senderId, Long receiverId, FriendStatus friendStatus) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.friendStatus = friendStatus;
    }
}
