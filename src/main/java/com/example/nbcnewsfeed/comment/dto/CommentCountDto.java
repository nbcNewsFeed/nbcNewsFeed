package com.example.nbcnewsfeed.comment.dto;

import lombok.Getter;

@Getter
public class CommentCountDto {

    private final Long postId;
    private final Long count;

    public CommentCountDto(Long postId, Long count) {
        this.postId = postId;
        this.count = count;
    }
}
