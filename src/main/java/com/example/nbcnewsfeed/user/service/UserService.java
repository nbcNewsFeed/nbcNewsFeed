package com.example.nbcnewsfeed.user.service;

import com.example.nbcnewsfeed.user.dto.UserResponseDto;
import com.example.nbcnewsfeed.user.entity.User;
import com.example.nbcnewsfeed.user.exception.CustomException;
import com.example.nbcnewsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // signup -> 회원가입
    public UserResponseDto signup(String email, String nickname, String password, String profileImageUrl, String statusMessage) {
        checkEmailDuplication(email);
        User signupUser = userRepository.save(new User(email, nickname, password, profileImageUrl, statusMessage));
        return new UserResponseDto(signupUser.getNickname(), signupUser.getStatusMessage(), signupUser.getProfileImageUrl());
    }

    // 중복 이메일 체크
    private void checkEmailDuplication(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new CustomException("이미 사용 중인 이메일입니다.", HttpStatus.CONFLICT);
        }
    }
}
