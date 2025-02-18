package com.example.nbcnewsfeed.post.controller;

import com.example.nbcnewsfeed.post.dto.request.PostDeleteRequestDto;
import com.example.nbcnewsfeed.post.dto.request.PostRestoreRequestDto;
import com.example.nbcnewsfeed.post.dto.request.PostSaveRequestDto;
import com.example.nbcnewsfeed.post.dto.request.PostUpdateRequestDto;
import com.example.nbcnewsfeed.post.dto.response.PostPageResponseDto;
import com.example.nbcnewsfeed.post.dto.response.PostResponseDto;
import com.example.nbcnewsfeed.post.dto.response.PostSaveResponseDto;
import com.example.nbcnewsfeed.post.dto.response.PostUpdateResponseDto;
import com.example.nbcnewsfeed.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "게시글 관리 API", description = "게시글을 관리하는 API 입니다.")
public class PostController {

    private final PostService postService;

    //게시글 등록(C)
    @PostMapping("/posts")
    @Operation(summary = "게시글 생성", description = "게시글을 생성합니다.")
    public ResponseEntity<PostSaveResponseDto> save(
            @Validated @RequestBody PostSaveRequestDto requestDto
    ) {
        //todo JWT에서 사용자 Id 추출 방식으로 변경 시 userId, requestDto 분리 필요
        return new ResponseEntity<>(postService.save(requestDto), HttpStatus.CREATED);
    }

    //게시글 다건 조회(R) 페이지네이션
    @GetMapping("/posts")
    @Operation(summary = "게시글 다건 조회", description = "게시글을 한 페이지에 10개씩 조회합니다.")
    public ResponseEntity<Page<PostPageResponseDto>> findAllPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<PostPageResponseDto> result = postService.findAllPage(page, size);
        return ResponseEntity.ok(result);
    }

    //게시글 단건 조회(R)
    @GetMapping("/posts/{postId}")
    @Operation(summary = "게시글 단건 조회", description = "특정 게시글을 조회합니다.")
    public ResponseEntity<PostResponseDto> findById(
            @PathVariable Long postId
    ) {
        return ResponseEntity.ok(postService.findById(postId));
    }


    //게시글 수정(U)
    @PutMapping("/posts/{postId}")
    @Operation(summary = "게시글 수정", description = "게시글을 수정합니다.")
    public ResponseEntity<PostUpdateResponseDto> update(
            @PathVariable Long postId,
            //todo JWT에서 사용자 Id 추출 방식으로 변경 시 userId, requestDto 분리 필요
            @Validated @RequestBody PostUpdateRequestDto requestDto
    ) {
        return ResponseEntity.ok(postService.update(
                postId,
                requestDto
        ));
    }

    //게시글 삭제(D)
    @DeleteMapping("/posts/{postId}")
    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long postId,
            //todo JWT에서 사용자 Id 추출 방식으로 변경 시 PostDeleterequestDto 삭제 필요
            PostDeleteRequestDto requestDto
    ) {
        //todo JWT에서 사용자 Id 추출 방식으로 변경 시 매개변수 userId로 수정 필요
        postService.deleteById(postId, requestDto);
        return ResponseEntity.ok().build();
    }

    //삭제된 게시글 복구(삭제된 후 2주 지나기 전)
    @PutMapping("/posts/{postId}")
    @Operation(summary = "게시글 복원", description = "삭제되었던 게시글을 복원합니다.")
    public ResponseEntity<PostResponseDto> restore(
            @PathVariable Long postId,
            //todo JWT에서 사용자 Id 추출
            PostRestoreRequestDto requestDto
    ) {
        return ResponseEntity.ok(postService.restore(postId, requestDto));
    }
}
