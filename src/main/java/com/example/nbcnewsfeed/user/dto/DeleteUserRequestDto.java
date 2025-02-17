package com.example.nbcnewsfeed.user.dto;

import lombok.Getter;

@Getter
public class DeleteUserRequestDto {

    private final String inputPassword;

    public DeleteUserRequestDto(String inputPassword) {
        this.inputPassword = inputPassword;
    }
}
