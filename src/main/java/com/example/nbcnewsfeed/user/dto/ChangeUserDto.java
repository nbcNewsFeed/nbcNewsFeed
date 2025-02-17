package com.example.nbcnewsfeed.user.dto;

import lombok.Getter;

@Getter
public class ChangeUserDto {
    private final String inputPassword;
    private final String newProfileImageUrl;
    private final String newNickname;
    private final String newStatusMessage;

    public ChangeUserDto(String inputPassword, String newProfileImageUrl, String newNickname, String newStatusMessage) {
        this.inputPassword = inputPassword;
        this.newProfileImageUrl = newProfileImageUrl;
        this.newNickname = newNickname;
        this.newStatusMessage = newStatusMessage;
    }
}
