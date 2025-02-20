package com.example.nbcnewsfeed.auth.service;

import com.example.nbcnewsfeed.user.entity.User;
import com.example.nbcnewsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * AuthService : 로그인을 처리하는 서비스
 * - 회원 레포지토리에서 email과 email, password 조회
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmailOrElseThrow(email);
    }

    @Transactional(readOnly = true)
    public User login(String email, String password) {
        return userRepository.findByEmailAndPasswordOrElseThrow(email,password);
    }
}
