package com.example.nbcnewsfeed.user.dto;

import lombok.Getter;

@Getter
public class PasswordChangeDto {
    private final String currentPassword;
    private final String newPassword;

    public PasswordChangeDto(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }
}
