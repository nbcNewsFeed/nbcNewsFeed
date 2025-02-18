package com.example.nbcnewsfeed.auth.service;

import com.example.nbcnewsfeed.user.entity.User;
import com.example.nbcnewsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
