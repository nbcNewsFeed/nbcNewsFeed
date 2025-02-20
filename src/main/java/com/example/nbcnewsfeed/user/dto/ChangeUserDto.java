package com.example.nbcnewsfeed.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

@Getter
public class ChangeUserDto {

    private final String inputPassword;

    @URL(message = "프로필 이미지 URL 형식이 아닙니다.")
    private final String newProfileImageUrl;

    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
    private final String newNickname;

    private final String newStatusMessage;

    public ChangeUserDto(String inputPassword, String newProfileImageUrl, String newNickname, String newStatusMessage) {
        this.inputPassword = inputPassword;
        this.newProfileImageUrl = newProfileImageUrl;
        this.newNickname = newNickname;
        this.newStatusMessage = newStatusMessage;
    }
}
