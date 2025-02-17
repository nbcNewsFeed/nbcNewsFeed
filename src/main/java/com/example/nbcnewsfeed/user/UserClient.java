package com.example.nbcnewsfeed.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserClient {

    // 유저 클라이언트 = 유저서비스와는 다른 역할임! (DDD의 구조를 지기키 위한..)

    @Autowired
    private FakeUserRepository userRepository;

    public UserClient(FakeUserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User findUserById(Long userId) {
        return userRepository.findUserByid(userId);
    }

}
