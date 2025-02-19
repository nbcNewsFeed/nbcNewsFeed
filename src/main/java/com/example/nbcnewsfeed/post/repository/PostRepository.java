package com.example.nbcnewsfeed.post.repository;

import com.example.nbcnewsfeed.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 삭제 요청 후 2주 지난 사용자 삭제
    List<Post> findAllByDeletedAtBefore(LocalDateTime twoWeeksAgo);

    Page<Post> findByUserIdIn(List<Long> friendIds, Pageable pageable);
}
