package com.example.nbcnewsfeed.comment.controller;

import com.example.nbcnewsfeed.comment.dto.request.CommentSaveRequestDto;
import com.example.nbcnewsfeed.comment.dto.request.CommentUpdateRequestDto;
import com.example.nbcnewsfeed.comment.dto.response.CommentResponseDto;
import com.example.nbcnewsfeed.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "댓글 관리 API", description = "댓글을 관리하는 API 입니다.")
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/comments/{postId}")
    @Operation(summary = "댓글 생성", description = "댓글을 생성합니다.")
    public ResponseEntity<CommentResponseDto> save(
            HttpServletRequest request,
            @PathVariable Long postId,
            @RequestBody CommentSaveRequestDto dto
    ) {
        Long userId = Long.parseLong(String.valueOf(request.getAttribute("userId")));
        return new ResponseEntity<>(commentService.save(userId, postId, dto), HttpStatus.CREATED);
    }

    // 댓글 조회
    @GetMapping("/comments/{postId}")
    @Operation(summary = "댓글 조회", description = "postId로 댓글을 조회합니다.")
    public ResponseEntity<List<CommentResponseDto>> findByPost(
            @PathVariable Long postId
    ) {
        return new ResponseEntity<>(commentService.findByPost(postId), HttpStatus.OK);
    }

    // 댓글 수정
    @PutMapping("/comments/{commentId}")
    @Operation(summary = "댓글 수정", description = "commentId로 댓글을 수정합니다.")
    public ResponseEntity<CommentResponseDto> update(
            @PathVariable Long commentId,
            HttpServletRequest request,
            @RequestBody CommentUpdateRequestDto dto
    ) {
        Long userId = Long.parseLong(String.valueOf(request.getAttribute("userId")));
        return new ResponseEntity<>(commentService.update(commentId, userId, dto), HttpStatus.OK);
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long commentId,
            HttpServletRequest request
    ) {
        Long userId = Long.parseLong(String.valueOf(request.getAttribute("userId")));
        commentService.deleteById(commentId, userId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}