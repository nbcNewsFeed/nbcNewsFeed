package com.example.nbcnewsfeed.friend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateFriendRequestDto {

    @NotNull
    private final Long senderId;

    @NotNull
    private final Long receiverId;

    @NotNull
    private final Boolean isAcceptOrReject;

    public UpdateFriendRequestDto(Long senderId, Long receiverId, Boolean isAcceptOrReject) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.isAcceptOrReject = isAcceptOrReject;
    }
}
