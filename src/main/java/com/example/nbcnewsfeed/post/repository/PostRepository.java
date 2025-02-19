package com.example.nbcnewsfeed.post.repository;
import com.example.nbcnewsfeed.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    // 삭제 요청 후 2주 지난 사용자 삭제
    List<Post> findAllByDeletedAtBefore(LocalDateTime twoWeeksAgo);

    Page<Post> findByUserIdIn(List<Long> friendIds, Pageable pageable);

    //필터 적용하여 단건 조회 메서드
    @Query("SELECT p FROM Post p WHERE p.id = :id")
    Optional<Post> findByIdWithFilter(@Param("id") Long id);

    default Post findByIdWithFilterOrElseThrow(Long id) {
        return findByIdWithFilter(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }
}
