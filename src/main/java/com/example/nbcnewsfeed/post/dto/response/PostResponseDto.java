package com.example.nbcnewsfeed.post.dto.response;

import com.example.nbcnewsfeed.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {

    private final Long id;
    private final Long userId;
    private final String imageUrl;
    private final String contents;
    private final Long numOfComments;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public PostResponseDto(
            Long id,
            Long userId,
            String imageUrl,
            String contents,
            Long numOfComments,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt) {
        this.id = id;
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.contents = contents;
        this.numOfComments = numOfComments;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.userId = post.getUser().getId();
        this.imageUrl = post.getImageUrl();
        this.contents = post.getContents();
        this.numOfComments = post.getNumOfComments();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();

    }
}
