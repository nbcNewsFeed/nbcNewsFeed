package com.example.nbcnewsfeed.comment.entity;

import com.example.nbcnewsfeed.common.entity.BaseEntity;
import com.example.nbcnewsfeed.post.entity.Post;
import com.example.nbcnewsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
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
