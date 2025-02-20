package com.example.nbcnewsfeed.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

@Getter
public class PostSaveRequestDto {

    @URL(message = "프로필 이미지 URL 형식이 아닙니다.")
    @NotBlank(message = "사진을 넣어주세요.")
    private String imageUrl;
    @NotBlank(message = "내용을 입력해 주세요.")
    private String contents;
}
