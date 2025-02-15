package com.example.nbcnewsfeed.user.dto;

import com.example.nbcnewsfeed.user.entity.User;
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

    public static UserResponseDto todto(User user) {
        return new UserResponseDto(user.getNickname(),user.getStatusMessage(),user.getProfileImageUrl());
    }
}
