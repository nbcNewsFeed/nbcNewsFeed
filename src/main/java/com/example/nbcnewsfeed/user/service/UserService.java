package com.example.nbcnewsfeed.user.service;

import com.example.nbcnewsfeed.user.dto.UserResponseDto;
import com.example.nbcnewsfeed.user.entity.User;
import com.example.nbcnewsfeed.user.exception.CustomException;
import com.example.nbcnewsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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
        User user = userRepository.findByIdOrElseThrow(currentUserId);
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

    // 사용자 수정
    // 비밀번호
    @Transactional
    public UserResponseDto updatePasswordUser(Long currentUserId, String inputPassword, String newPassword) {
        User user = userRepository.findByIdOrElseThrow(currentUserId);
        validPassword(inputPassword, user.getPassword());
        if (sanitizeString(newPassword) != null) {
            user.setPassword(newPassword);
        }
        return new UserResponseDto(user.getNickname(), user.getStatusMessage(), user.getProfileImageUrl());
    }

    // 사용자 삭제 요청
    @Transactional
    public void deleteUser(Long currentUserId, String inputPassword) {
        User user = userRepository.findByIdOrElseThrow(currentUserId);
        validPassword(inputPassword, user.getPassword());
        user.setDeletedAt(LocalDateTime.now());
    }

    // 삭제된 사용자 복구
    @Transactional
    public UserResponseDto restoreUser(String email, String password) {
        User user = userRepository.findByEmailOrElseThrow(email);
        validPassword(password, user.getPassword());
        user.setDeletedAt(null);
        return new UserResponseDto(user.getNickname(), user.getStatusMessage(), user.getProfileImageUrl());
    }

    // 2주가 지난 사용자 물리 삭제
    @Transactional
    @Scheduled(cron = "0 0 3 * * ?")
    public void deleteUsers() {
        LocalDateTime twoWeeksAgo = LocalDateTime.now().minusWeeks(2);
        List<User> userToDelete = userRepository.findAllByDeletedAtBefore(twoWeeksAgo);
        if (!userToDelete.isEmpty()) {
            userRepository.deleteAll(userToDelete);
        }
    }

    // 중복 이메일 체크
    private void checkEmailDuplication(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new CustomException("이미 사용 중인 이메일입니다.", HttpStatus.CONFLICT);
        }
    }

    // 공백 제거 및 빈 문자열 null로 변환
    private String sanitizeString(String input) {
        return StringUtils.hasText(input) ? input : null;
    }

    // 비밀번호 검증 메소드
    private void validPassword(String inputPassword, String rawPassword) {
        if (!rawPassword.equals(inputPassword)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

}
