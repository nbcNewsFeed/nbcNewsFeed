package com.example.nbcnewsfeed.user.repository;

import com.example.nbcnewsfeed.user.entity.User;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> findUsers(Long id, String nickname);
}
