package com.example.nbcnewsfeed.user.repository;

import com.example.nbcnewsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 이메일 존재 여부 확인
    boolean existsByEmail(String email);

    // 아이디, 닉네임으로 사용자 조회
    @Query("SELECT u FROM User u WHERE " +
            "(:id IS NULL OR u.id = :id) AND " +
            "(:nickname IS NULL OR u.nickname LIKE CONCAT('%', :nickname, '%'))")
    List<User> findUsers(
            @Param("id") Long id,
            @Param("nickname") String nickname
    );

}
