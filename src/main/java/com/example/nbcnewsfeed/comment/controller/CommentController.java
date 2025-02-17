package com.example.nbcnewsfeed.comment.controller;

import com.example.nbcnewsfeed.comment.dto.request.CommentSaveRequestDto;
import com.example.nbcnewsfeed.comment.dto.request.CommentUpdateRequestDto;
import com.example.nbcnewsfeed.comment.dto.response.CommentResponseDto;
import com.example.nbcnewsfeed.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponseDto> save(
            @SessionAttribute(name = "LOGIN_USER") Long userId,
            @PathVariable Long postId,
            @RequestBody CommentSaveRequestDto dto
    ) {
        return ResponseEntity.ok(commentService.save(userId, postId, dto));
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponseDto>> findByPost(
            @PathVariable Long postId
    ) {
        return ResponseEntity.ok(commentService.findByPost(postId));
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDto> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.findOne(id));
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDto> update(
            @SessionAttribute(name = "LOGIN_USER") Long userId,
            @PathVariable Long id,
            @RequestBody CommentUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(commentService.update(id, userId, dto));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> delete(
            @SessionAttribute(name = "LOGIN_USER") Long userId,
            @PathVariable Long id
    ) {
        commentService.delete(id, userId);
        return ResponseEntity.ok().build();
    }
}