package com.example.nbcnewsfeed.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(name = "user")
@Filter(name = "activeUserFilter", condition = "deleted_at is null")
@FilterDef(name = "activeUserFilter")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Column(nullable = false)
    private String nickname;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Column(nullable = false, unique = true)
    private String email;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대소문자, 숫자, 특수문자를 사용하세요.")
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


    public User(String email, String nickname, String password, String profileImageUrl, String statusMessage) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
        this.statusMessage = statusMessage;
    }

    public User() {

    }
}
