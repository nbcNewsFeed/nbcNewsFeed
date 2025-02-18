package com.example.nbcnewsfeed.post.entity;

import com.example.nbcnewsfeed.common.entity.BaseEntity;
import com.example.nbcnewsfeed.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "posts")
@Filter(name = "activePostFilter", condition = "deleted_at is null")
@FilterDef(name = "activePostFilter")
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//  User class N:1 연관관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "contents")
    private String contents;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


    public Post(
            User user,
            String imageUrl,
            String contents
    ) {
        this.user = user;
        this.imageUrl = imageUrl;
        this.contents = contents;
    }

    public void update(
            @NotBlank(message = "사진을 넣어주세요.")
            String imageUrl,
            @NotBlank(message = "내용을 입력해 주세요.")
            String contents) {
        this.imageUrl = imageUrl;
        this.contents = contents;
    }

    public void createDeletedAt(LocalDateTime now) {
        this.deletedAt = now;
    }
}
