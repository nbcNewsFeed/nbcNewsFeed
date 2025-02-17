package com.example.nbcnewsfeed.user;

import com.example.nbcnewsfeed.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Column(nullable = false)
    private String nickname;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Column(nullable = false)
    private String password;

    @URL(message = "프로필 이미지 URL 형식이 아닙니다.")
    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "status_message")
    private String statusMessage;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public User(String nickname, String email, String password, String profileImageUrl, LocalDateTime deletedAt, String statusMessage) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
        this.deletedAt = deletedAt;
        this.statusMessage = statusMessage;
    }

    public User() {

    }
}
