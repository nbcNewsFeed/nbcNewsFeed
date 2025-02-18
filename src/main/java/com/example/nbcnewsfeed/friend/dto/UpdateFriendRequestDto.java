package com.example.nbcnewsfeed.friend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateFriendRequestDto { //친구 수락 및 거절하는 dto

    @NotNull
    private final Boolean isAcceptOrReject;

    public UpdateFriendRequestDto(Boolean isAcceptOrReject) {
        this.isAcceptOrReject = isAcceptOrReject;
    }
}
