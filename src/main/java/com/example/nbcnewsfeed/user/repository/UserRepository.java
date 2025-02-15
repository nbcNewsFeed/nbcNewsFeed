package com.example.nbcnewsfeed.user.repository;

import com.example.nbcnewsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 이메일 존재 여부 확인
    boolean existsByEmail(String email);

}
