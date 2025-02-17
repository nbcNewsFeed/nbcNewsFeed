package com.example.nbcnewsfeed.friend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeleteFriendshipDto {

    @NotBlank
    private final Long user1Id;

    @NotBlank
    private final Long user2Id;

    public DeleteFriendshipDto(Long user2Id, Long user1Id) {
        this.user2Id = user2Id;
        this.user1Id = user1Id;
    }
}
