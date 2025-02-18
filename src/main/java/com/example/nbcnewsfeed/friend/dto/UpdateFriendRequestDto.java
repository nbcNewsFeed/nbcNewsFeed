package com.example.nbcnewsfeed.friend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateFriendRequestDto {

    @NotNull
    private final Boolean isAcceptOrReject;

    public UpdateFriendRequestDto(Boolean isAcceptOrReject) {
        this.isAcceptOrReject = isAcceptOrReject;
    }
}
