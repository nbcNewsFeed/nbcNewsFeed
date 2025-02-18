package com.example.nbcnewsfeed.comment.controller;

import com.example.nbcnewsfeed.comment.dto.request.CommentSaveRequestDto;
import com.example.nbcnewsfeed.comment.dto.request.CommentUpdateRequestDto;
import com.example.nbcnewsfeed.comment.dto.response.CommentResponseDto;
import com.example.nbcnewsfeed.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
            @SessionAttribute(name = "LOGIN_USER") Long userId,
            @PathVariable Long postId,
            @RequestBody CommentSaveRequestDto dto
    ) {
        return ResponseEntity.ok(commentService.save(userId, postId, dto));
    }

    // 전체 댓글 조회
    @GetMapping("/comments/{postId}")
    @Operation(summary = "댓글 조회", description = "postId로 댓글을 조회합니다.")
    public ResponseEntity<List<CommentResponseDto>> findByPost(
            @PathVariable Long postId
    ) {
        return ResponseEntity.ok(commentService.findByPost(postId));
    }

    // 댓글 단건 조회
    @GetMapping("/comments/{postId}/{commentId}")
    @Operation(summary = "댓글 단건 조회", description = "postId, commentId로 댓글을 조회합니다.")
    public ResponseEntity<CommentResponseDto> findOne(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(commentService.findOne(id));
    }

    // 댓글 수정
    @PutMapping("/comments/{postId}/{commentsId}")
    @Operation(summary = "댓글 수정", description = "postId, commentId로 댓글을 수정합니다.")
    public ResponseEntity<CommentResponseDto> update(
            @SessionAttribute(name = "LOGIN_USER") Long userId,
            @PathVariable Long id,
            @RequestBody CommentUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(commentService.update(id, userId, dto));
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{postId}/{commentId}")
    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    public ResponseEntity<Void> delete(
            @SessionAttribute(name = "LOGIN_USER") Long userId,
            @PathVariable Long id
    ) {
        commentService.delete(id, userId);
        return ResponseEntity.ok().build();
    }
}