package com.example.nbcnewsfeed.friend.dto;

import com.example.nbcnewsfeed.friend.entity.Friendship;
import lombok.Getter;


@Getter
public class FriendshipListDto { //현재 친구관계 목록 반환 dto

    private final Long user1Id;
    private final Long user2Id;

    public FriendshipListDto(Long user1Id, Long user2Id) {
        this.user1Id = user1Id;
        this.user2Id = user2Id;
    }

    public static FriendshipListDto toDto(Friendship friendship) {
        return new FriendshipListDto(
                friendship.getUser1().getId(),
                friendship.getUser2().getId()
        );
    }


}
