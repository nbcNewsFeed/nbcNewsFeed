package com.example.nbcnewsfeed.post.entity;

import com.example.nbcnewsfeed.common.entity.BaseEntity;
import com.example.nbcnewsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "post")
@FilterDef(name = "activePostFilter")
@Filter(name = "activePostFilter", condition = "deleted_at is null " +
"AND user_id IN (SELECT u.id FROM user u WHERE u.deleted_at IS NULL)")
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
    private Long numOfComments;

    public Post(
            User user,
            String imageUrl,
            String contents,
            Long numOfComments
    ) {
        this.user = user;
        this.imageUrl = imageUrl;
        this.contents = contents;
        this.numOfComments = numOfComments;
    }

    public void update(
            String imageUrl,
            String contents) {
        this.imageUrl = imageUrl;
        this.contents = contents;
    }

    public void createDeletedAt(LocalDateTime now) {
        this.deletedAt = now;
    }

    public void updateNumOfComments(long numOfComments) {
        this.numOfComments = numOfComments;
    }
}
