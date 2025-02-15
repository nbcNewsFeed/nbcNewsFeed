package com.example.nbcnewsfeed.user.controller;

import com.example.nbcnewsfeed.user.dto.UserResponseDto;
import com.example.nbcnewsfeed.user.dto.UserSignupDto;
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
    public ResponseEntity<UserResponseDto> userSignup(@Valid @RequestBody UserSignupDto signupDto) {
        UserResponseDto responseDto = userService.signup(
                signupDto.getEmail(),
                signupDto.getNickname(),
                signupDto.getPassword(),
                signupDto.getProfileImageUrl(),
                signupDto.getStatusMessage()
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
            @RequestParam String inputPassword,
            @RequestParam(required = false) String newProfileImageUrl,
            @RequestParam(required = false) String newNickname,
            @RequestParam(required = false) String newStatusMessage,
            HttpServletRequest request
    ){
        Long currentUserId = getCurrentUserId(request);
        UserResponseDto userResponseDto = userService.updateUser(currentUserId,inputPassword,newProfileImageUrl,newNickname,newStatusMessage);
        return new ResponseEntity<>(userResponseDto,HttpStatus.OK);
    }

    // 세션 검증 로직
    private Long getCurrentUserId(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("userId") == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "권한이 없습니다.");
        }
        return (Long)session.getAttribute("userId");
    }
}
