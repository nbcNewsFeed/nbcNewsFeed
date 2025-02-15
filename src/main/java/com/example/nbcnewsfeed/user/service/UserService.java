package com.example.nbcnewsfeed.user.service;

import com.example.nbcnewsfeed.user.dto.UserResponseDto;
import com.example.nbcnewsfeed.user.entity.User;
import com.example.nbcnewsfeed.user.exception.CustomException;
import com.example.nbcnewsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    // 아이디, 닉네임으로 사용자 조회 (단건 + 다건)
    public List<UserResponseDto> findUsers(Long id, String nickname) {
        List<User> userList = userRepository.findUsers(id, sanitizeString(nickname));
        if (userList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return userList.stream().map(UserResponseDto::todto).toList();
    }

    // 사용자 수정
    // 프로필 사진, 닉네임, 한 줄 소개
    @Transactional
    public UserResponseDto updateUser(Long currentUserId, String inputPassword, String newProfileImageUrl, String newNickname, String newStatusMessage) {
        User user = userRepository.findByIdElseOrThrow(currentUserId);
        validPassword(inputPassword, user.getPassword());
        if (sanitizeString(newProfileImageUrl) != null) {
            user.setProfileImageUrl(newProfileImageUrl);
        }
        if (sanitizeString(newStatusMessage) != null) {
            user.setStatusMessage(newStatusMessage);
        }
        if (sanitizeString(newNickname) != null) {
            user.setNickname(newNickname);
        }
        return new UserResponseDto(user.getNickname(), user.getStatusMessage(), user.getProfileImageUrl());
    }

    // 중복 이메일 체크
    private void checkEmailDuplication(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new CustomException("이미 사용 중인 이메일입니다.", HttpStatus.CONFLICT);
        }
    }

    // 공백 제거 및 빈 문자열 null로 변환
    private String sanitizeString(String input) {
        return (input != null && !input.trim().isEmpty()) ? input : null;
    }

    // 비밀번호 검증 메소드
    private void validPassword(String inputPassword, String rawPassword) {
        if (!rawPassword.equals(inputPassword)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

}
