package com.example.nbcnewsfeed.user.entity;

import com.example.nbcnewsfeed.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import java.time.LocalDateTime;


@Entity
@Getter
@Table(name = "user")
@Filter(name = "activeUserFilter", condition = "deleted_at is null")
@FilterDef(name = "activeUserFilter")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "status_message")
    private String statusMessage;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


    public User(String email, String nickname, String password, String profileImageUrl, String statusMessage) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
        this.statusMessage = statusMessage;
    }

    public User() {

    }

    // 수정 메소드
    public void changeProfileImageUrl(String newProfileImageUrl) {
        this.profileImageUrl = newProfileImageUrl;
    }

    public void changeStatusMessage(String newStatusMessage) {
        this.statusMessage = newStatusMessage;
    }

    public void changeNickname(String newNickname) {
        this.nickname = newNickname;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void changeDeletedAt(LocalDateTime newDateTime) {
        this.deletedAt = newDateTime;
    }
}
