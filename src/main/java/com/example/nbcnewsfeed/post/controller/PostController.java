package com.example.nbcnewsfeed.post.controller;

import com.example.nbcnewsfeed.post.dto.request.PostSaveRequestDto;
import com.example.nbcnewsfeed.post.dto.response.PostSaveResponseDto;
import com.example.nbcnewsfeed.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    //게시글 등록(C)
    @PostMapping("/posts")
    public PostSaveResponseDto save(
            //토큰 받아 올 자리
//            @SessionAttribute(name = Const.LOGIN_USER) Long userId;
            //requestDto 받는 자리
            @Validated @RequestBody PostSaveRequestDto requestDto
    ) {
        return postService.save(requestDto);
    }
}
