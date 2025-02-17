package com.example.nbcnewsfeed.friend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateFriendRequestDto {

    @NotBlank
    private final Long senderId;

    @NotBlank
    private final Long receiverId;


    public CreateFriendRequestDto(Long senderId, Long receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
    }
}
