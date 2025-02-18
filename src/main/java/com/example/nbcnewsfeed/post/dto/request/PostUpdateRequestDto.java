package com.example.nbcnewsfeed.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostUpdateRequestDto {

    @NotBlank(message = "사진을 넣어주세요.")
    private String imageUrl;
    @NotBlank(message = "내용을 입력해 주세요.")
    private String contents;
}
