package com.example.nbcnewsfeed.user.controller;

import com.example.nbcnewsfeed.user.dto.*;
import com.example.nbcnewsfeed.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "사용자 관리 API", description = "사용자를 관리하는 API 입니다.")
public class UserController {

    private final UserService userService;

    // signup -> 회원가입
    @PostMapping("/signup")
    @Operation(summary = "사용자 생성", description = "사용자를 생성합니다.")
    public ResponseEntity<UserResponseDto> userSignup(@Valid @RequestBody UserSignupDto requestDto) {
        UserResponseDto responseDto = userService.signup(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 사용자 조회
    @GetMapping
    @Operation(summary = "사용자 조회", description = "아이디, 닉네임으로 사용자를 단건, 다건 조회합니다.")
    public ResponseEntity<List<UserResponseDto>> findUsers(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String nickname
    ) {
        List<UserResponseDto> userResponseDtoList = userService.findUsers(id, nickname);
        return new ResponseEntity<>(userResponseDtoList, HttpStatus.OK);
    }

    // 사용자 수정 -> 프로필 사진, 닉네임, 한 줄 소개
    @PatchMapping("/me")
    @Operation(summary = "사용자 수정", description = "로그인한 사용자의 닉네임, 프로필 사진, 소개를 수정합니다.")
    public ResponseEntity<UserResponseDto> updateUser(
            @Valid @RequestBody ChangeUserDto requestDto,
            HttpServletRequest request
    ) {
        Long userId = Long.parseLong(String.valueOf(request.getAttribute("userId")));
//        String email = (String) request.getAttribute("email");
        log.info("userController userId={}",userId);
//        Long currentUserId = getCurrentUserId(request);
        UserResponseDto userResponseDto = userService.updateUser(userId, requestDto);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    // 사용자 수정 -> 비밀번호
    @PatchMapping("me/password")
    @Operation(summary = "사용자 수정", description = "로그인한 사용자의 비밀번호를 수정합니다.")
    public ResponseEntity<UserResponseDto> updatePasswordUser(
            @Valid @RequestBody ChangePasswordDto requestDto,
            HttpServletRequest request
    ) {
        Long currentUserId = getCurrentUserId(request);
        UserResponseDto userResponseDto = userService.updatePasswordUser(currentUserId, requestDto);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    // 사용자 삭제
    @DeleteMapping
    @Operation(summary = "사용자 삭제", description = "비밀번호 일치 시 사용자를 삭제합니다.")
    public ResponseEntity<Void> deleteUser(
            @RequestBody DeleteUserRequestDto requestDto,
            HttpServletRequest request) {
        Long currentUserId = getCurrentUserId(request);
        userService.deleteUser(currentUserId, requestDto);
        request.getSession(false).invalidate();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 삭제된 사용자 복구
    @PutMapping
    @Operation(summary = "사용자 복구", description = "이메일과 비밀번호 입력 시, 사용자를 복구합니다.")
    public ResponseEntity<UserResponseDto> restoreUser(
            @RequestBody RestoreUserDto requestDto
    ) {
        UserResponseDto userResponseDto = userService.restoreUser(requestDto);
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
