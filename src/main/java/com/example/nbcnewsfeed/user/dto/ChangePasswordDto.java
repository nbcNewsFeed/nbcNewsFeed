package com.example.nbcnewsfeed.user.dto;

import lombok.Getter;

@Getter
public class ChangePasswordDto {
    private final String currentPassword;
    private final String newPassword;

    public ChangePasswordDto(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }
}
