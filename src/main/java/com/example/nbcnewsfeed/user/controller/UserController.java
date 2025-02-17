package com.example.nbcnewsfeed.user.controller;

import com.example.nbcnewsfeed.user.dto.*;
import com.example.nbcnewsfeed.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // signup -> 회원가입
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> userSignup(@Valid @RequestBody UserSignupDto requestDto) {
        UserResponseDto responseDto = userService.signup(
                requestDto.getEmail(),
                requestDto.getNickname(),
                requestDto.getPassword(),
                requestDto.getProfileImageUrl(),
                requestDto.getStatusMessage()
        );
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 사용자 조회
    // 아이디, 닉네임으로 조회 + 전체 조회
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findUsers(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String nickname
    ) {
        List<UserResponseDto> userResponseDtoList = userService.findUsers(id, nickname);
        return new ResponseEntity<>(userResponseDtoList, HttpStatus.OK);
    }

    // 사용자 수정
    // 프로필 사진, 닉네임, 한 줄 소개
    @PatchMapping("/me")
    public ResponseEntity<UserResponseDto> updateUser(
            @RequestBody ChangeUserDto requestDto,
            HttpServletRequest request
    ) {
        Long currentUserId = getCurrentUserId(request);
        UserResponseDto userResponseDto = userService.updateUser(currentUserId,
                requestDto.getInputPassword(),
                requestDto.getNewProfileImageUrl(),
                requestDto.getNewNickname(),
                requestDto.getNewStatusMessage()
        );
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    // 사용자 수정 -> 비밀번호
    @PatchMapping("me/password")
    public ResponseEntity<UserResponseDto> updatePasswordUser(
            @Valid @RequestBody ChangePasswordDto requestDto,
            HttpServletRequest request
    ) {
        Long currentUserId = getCurrentUserId(request);
        UserResponseDto userResponseDto = userService.updatePasswordUser(currentUserId,
                requestDto.getCurrentPassword(),
                requestDto.getNewPassword()
        );
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    // 사용자 삭제
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(
            @RequestBody DeleteUserRequestDto requestDto,
            HttpServletRequest request) {
        Long currentUserId = getCurrentUserId(request);
        userService.deleteUser(currentUserId, requestDto.getInputPassword());
        request.getSession(false).invalidate();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 삭제된 사용자 복구
    @PutMapping
    public ResponseEntity<UserResponseDto> restoreUser(
            @RequestBody RestoreUserDto requestDto
    ) {
        UserResponseDto userResponseDto = userService.restoreUser(requestDto.getEmail(), requestDto.getPassword());
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    // 권한 세션 검증 로직
    private Long getCurrentUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "권한이 없습니다.");
        }
        return (Long) session.getAttribute("userId");
    }
}
