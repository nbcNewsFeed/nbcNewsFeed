package com.example.nbcnewsfeed.user.controller;

import com.example.nbcnewsfeed.user.dto.UserResponseDto;
import com.example.nbcnewsfeed.user.dto.UserSignupDto;
import com.example.nbcnewsfeed.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    ){
        List<UserResponseDto> userResponseDtoList = userService.findUsers(id, nickname);
        return new ResponseEntity<>(userResponseDtoList,HttpStatus.OK);
    }
}
