package com.example.nbcnewsfeed.comment.entity;

import com.example.nbcnewsfeed.common.entity.BaseEntity;
import com.example.nbcnewsfeed.post.entity.Post;
import com.example.nbcnewsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;

@Getter
@Entity
@NoArgsConstructor
@Filter(name = "activeUserFilter", condition = "user_id IN (SELECT id FROM user WHERE deleted_at IS NULL)")
@FilterDef(name = "activeUserFilter")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Comment(Post post, User user, String content){
        this.post = post;
        this.user = user;
        this.content = content;
    }

    // update
    public void update(String content) {
        this.content = content;
    }
}
