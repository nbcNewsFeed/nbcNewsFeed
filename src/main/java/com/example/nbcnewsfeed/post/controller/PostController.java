package com.example.nbcnewsfeed.post.controller;

import com.example.nbcnewsfeed.post.dto.request.PostSaveRequestDto;
import com.example.nbcnewsfeed.post.dto.request.PostUpdateRequestDto;
import com.example.nbcnewsfeed.post.dto.response.PostResponseDto;
import com.example.nbcnewsfeed.post.dto.response.PostSaveResponseDto;
import com.example.nbcnewsfeed.post.dto.response.PostUpdateResponseDto;
import com.example.nbcnewsfeed.post.entity.Post;
import com.example.nbcnewsfeed.post.repository.PostRepository;
import com.example.nbcnewsfeed.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    //게시글 등록(C)
    @PostMapping("/posts")
    public ResponseEntity<PostSaveResponseDto> save(
            //토큰 받아 올 자리
//            @SessionAttribute(name = Const.LOGIN_USER) Long userId;
            @Validated @RequestBody PostSaveRequestDto requestDto
    ) {
        return new ResponseEntity<>(postService.save(
//                userId,
                requestDto), HttpStatus.CREATED);
    }

    //게시글 다건 조회(R)
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> findAll() {
        return ResponseEntity.ok(postService.findAll());
    }

    //게시글 단건 조회(R)
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> findById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(postService.findById(id));
    }

    //게시글 수정(U)
    @PutMapping("/posts/{id}")
    public ResponseEntity<PostUpdateResponseDto> update(
            @PathVariable Long id,
            //토큰 받아 올 자리
//            @SessionAttribute(name = Const.LOGIN_USER) Long userId;
            @Validated @RequestBody PostUpdateRequestDto requestDto
            ) {
        return ResponseEntity.ok(postService.update(
                id,
//                userId,
                requestDto
        ));
    }

    //게시글 삭제(D)
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id
            //토큰 받아 올 자리
//            , @SessionAttribute(name = Const.LOGIN_USER) Long userId;

    ) {
        postService.deleteById(
                id
//                , userId
        );
        return ResponseEntity.ok().build();
    }
}
