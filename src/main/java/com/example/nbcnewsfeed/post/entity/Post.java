package com.example.nbcnewsfeed.post.entity;

import com.example.nbcnewsfeed.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Lazy;

@Getter
@Entity
@NoArgsConstructor
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Usre class N:1 연관관계 매핑
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
    private String imageUrl;
    private String contents;

    public Post(
//            User user,
            String imageUrl,
            String contents
    ) {
//        this.user = user;
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
}
