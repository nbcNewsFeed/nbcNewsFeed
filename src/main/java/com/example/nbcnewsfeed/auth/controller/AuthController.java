package com.example.nbcnewsfeed.auth.controller;

import com.example.nbcnewsfeed.auth.dto.LoginRequestDto;
import com.example.nbcnewsfeed.auth.jwt.util.JwtUtil;
import com.example.nbcnewsfeed.auth.service.AuthService;
import com.example.nbcnewsfeed.common.config.PasswordEncoder;
import com.example.nbcnewsfeed.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "로그인 인증 API", description = "로그인 인증하는 API 입니다.")
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인합니다.")
    public ResponseEntity<String> auth(@Valid @RequestBody LoginRequestDto dto) {
        log.info("AuthController dto ={}",dto);
        User user = authService.findByEmail(dto.getEmail());

        log.info("AuthController user ={}",user);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 회원 정보가 없으면 로그인 실패 (401 반환)
        }

        String encryptPassword = user.getPassword(); // DB에서 암호화된 비밀번호

        log.info("AuthController encryptPassword ={}",encryptPassword);

        if (passwordEncoder.matches(dto.getPassword(), encryptPassword)) {
            User loginUser = authService.login(dto.getEmail(), encryptPassword);
            log.info("AuthController loginUser ={}",loginUser);

//            String token = jwtUtil.createJwt(loginUser.getEmail(), Authority.USER);
            String token = jwtUtil.createJwt(loginUser.getId(),loginUser.getEmail());
            log.info("AuthController token ={}",token);

            // 응답 헤더에 JWT 추가
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", token);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body("Login successful");
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(@RequestHeader(value=JwtUtil.ACCESS_HEADER_STRING, required=false) String accessToken) {
//
//        if(accessToken == null) {
//            throw new IllegalStateException();
//        }
//
//        String actualToken = accessToken.replaceFirst("Bearer ", ""); // Bearer 제거
//        Jws<Claims> claimsJws;
//        try {
//            // 토큰 읽어서 memberId 알아내기
//            claimsJws = Jwts.parser()
//                    .verifyWith(JwtUtil.getSigningKey())
//                    .build()
//                    .parseSignedClaims(actualToken);
//        } catch(JwtException e) {
//            throw new IllegalStateException();
//        }
//
//        Long memberId = Long.parseLong(claimsJws.getPayload().getSubject());
//
//        jwtService.logout(memberId); // refresh, access token을 DB에서 지워주기
//
//        SuccessResult result = new Builder(DetailedStatus.OK)
//                .message("로그아웃 되었습니다.")
//                .build();
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
}
