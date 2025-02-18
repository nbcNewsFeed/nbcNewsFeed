package com.example.nbcnewsfeed.auth.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    // Bearer 접두사로, JWT 토큰 앞에 붙는 표준 문자열(Authorization: Bearer <JWT> 형식)
    private static final String BEARER_PREFIX = "Bearer ";
    private static final Long EXPIRATION = 30 * 60 * 1000L;
    private final SecretKey secretKey;

    public JwtUtil(@Value("${jwt.secret}") String secretkey) {
        this.secretKey = Keys.hmacShaKeyFor(secretkey.getBytes(StandardCharsets.UTF_8));
    }

    // JWT 생성
    public String createJwt(Long userId, String email) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                    .setSubject(String.valueOf(userId))
                    .claim("email",email) //이메일 저장
//                    .claim("authority",authority.name()) // JWT의 클레임(토큰에 담기는 정보)에 사용자의 권한(USER, ADMIN) ENUM 값 추가
                    .setIssuedAt(new Date()) // 발급 시간
                    .setExpiration(new Date(date.getTime() + EXPIRATION)) // 만료 시간 설정(30분)
                    .signWith(secretKey, SignatureAlgorithm.HS256)  // 키와 알고리즘을 사용하여 JWT에 서명을 추가
                    .compact(); // 최종적으로 토큰을 생성하고 반환
    }

    // JWT 추출 메서드
    public String subStringToken(String tokenValue){
        // 토큰 존재하고 "Bearer"로 시작하는지 확인
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)){
            return tokenValue.substring(7); // 접두사인 "Bearer "제거하고 JWT(토큰) 반환
        }
        log.error("Not Found Token");
        throw new NullPointerException("Not Found Token");
    }

   // JWT 파싱후 Claims 추출하는 메서드(Claims = 페이로드. 즉, 토큰에 담긴 사용자 정보, 만료 시간)
    public Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey) // 1. 서명을 검증할 키 설정(JWT 위,변조 여부 확인)
                .build() // 2. JWT 파서 생성
                .parseClaimsJwt(token)// 3. 토큰 파싱 및 서명 검증
                .getBody();// 4. 페이로드(Claims) 추출
    }

}