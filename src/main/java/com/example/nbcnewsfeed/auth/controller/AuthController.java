package com.example.nbcnewsfeed.auth.controller;

import com.example.nbcnewsfeed.auth.dto.LoginRequestDto;
import com.example.nbcnewsfeed.auth.jwt.util.JwtUtil;
import com.example.nbcnewsfeed.auth.service.AuthService;
import com.example.nbcnewsfeed.common.config.PasswordEncoder;
import com.example.nbcnewsfeed.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthController : 로그인을 처리하는 컨트롤러
 * - 로그인 성공 시 세션 생성, 사용자 정보 저장
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "로그인 인증 API", description = "로그인 인증하는 API 입니다.")
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * 로그인
     * @param dto
     * DB에서 이메일과 비밀번호를 확인 후, JWT 토큰을 발급하여 인증합니다.
     * @return "Login successful"
     */
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인합니다.")
    public ResponseEntity<String> auth(@Valid @RequestBody LoginRequestDto dto) {
        User user = authService.findByEmail(dto.getEmail());

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 회원 정보가 없으면 로그인 실패 (401 반환)
        }

        String encryptPassword = user.getPassword(); // DB에서 암호화된 비밀번호
        if (passwordEncoder.matches(dto.getPassword(), encryptPassword)) {
            User loginUser = authService.login(dto.getEmail(), encryptPassword);

            String token = jwtUtil.createJwt(loginUser.getId(),loginUser.getEmail());

            HttpHeaders headers = new HttpHeaders(); // 응답 헤더에 JWT 추가
            headers.add("Authorization", token);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body("Login successful");
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
