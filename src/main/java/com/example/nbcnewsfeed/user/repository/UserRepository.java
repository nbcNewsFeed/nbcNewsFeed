package com.example.nbcnewsfeed.user.repository;

import com.example.nbcnewsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

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

    // 아이디로 사용자 조회
    default User findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 아이디는 존재하지 않습니다."));
    }

    // 삭제 요청 후 2주 지난 사용자 삭제
    List<User> findAllByDeletedAtBefore(LocalDateTime dateTime);

    // 이메일로 사용자 찾기
    Optional<User> findByEmail(String email);

    default User findByEmailOrElseThrow(String email) {
        return findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 이메일은 존재하지 않습니다."));
    }

    Optional<User> findByEmailAndPassword(String email, String password);

    default User findByEmailAndPasswordOrElseThrow(String email, String password) {
        return findByEmailAndPassword(email,password).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 이메일과 비밀번호는 존재하지 않습니다."));
    }
}
