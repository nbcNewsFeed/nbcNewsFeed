package com.example.nbcnewsfeed.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeleteUserRequestDto {

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private final String inputPassword;

    public DeleteUserRequestDto(String inputPassword) {
        this.inputPassword = inputPassword;
    }
}
