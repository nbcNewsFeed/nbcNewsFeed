package com.example.nbcnewsfeed.post.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostPageResponseDto {

    private final Long id;
    private final Long userId;
    private final String imageUrl;
    private final String contents;
    private final int commentCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public PostPageResponseDto(Long id, Long userId, String imageUrl, String contents, int commentCount,LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.contents = contents;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
