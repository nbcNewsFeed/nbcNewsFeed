package com.example.nbcnewsfeed.user.dto;

import lombok.Getter;

@Getter
public class UserResponseDto {
    public final String nickname;
    public final String statusMessage;
    public final String profileImageUrl;

    public UserResponseDto(String nickname, String statusMessage, String profileImageUrl) {
        this.nickname = nickname;
        this.statusMessage = statusMessage;
        this.profileImageUrl = profileImageUrl;
    }
}
