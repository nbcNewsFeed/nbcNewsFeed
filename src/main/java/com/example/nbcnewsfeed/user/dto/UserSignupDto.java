package com.example.nbcnewsfeed.user.dto;

import lombok.Getter;

@Getter
public class UserSignupDto {
    public final String email;
    public final String nickname;
    public final String password;
    public final String statusMessage;
    public final String profileImageUrl;

    public UserSignupDto(String email, String nickname, String password, String statusMessage, String profileImageUrl) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.statusMessage = statusMessage;
        this.profileImageUrl = profileImageUrl;
    }
}
