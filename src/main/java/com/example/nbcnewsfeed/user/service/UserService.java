package com.example.nbcnewsfeed.user.service;

import com.example.nbcnewsfeed.user.dto.UserResponseDto;
import com.example.nbcnewsfeed.user.entity.User;
import com.example.nbcnewsfeed.user.exception.CustomException;
import com.example.nbcnewsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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
        if(userList.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return userList.stream().map(UserResponseDto::todto).toList();
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
}
