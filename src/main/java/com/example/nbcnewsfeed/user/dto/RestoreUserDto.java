package com.example.nbcnewsfeed.user.dto;

import lombok.Getter;

@Getter
public class RestoreUserDto {
    private final String email;
    public final String password;

    public RestoreUserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
