package com.example.nbcnewsfeed.comment.repository;

import com.example.nbcnewsfeed.comment.dto.CommentCountDto;
import com.example.nbcnewsfeed.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);

    @Query("select new com.example.nbcnewsfeed.comment.dto.CommentCountDto(c.post.id, count(c)) " +
            "from Comment c " +
            "where c.post.id in :postIds " +
            "group by c.post.id")
    List<CommentCountDto> countByPostIds(List<Long> postIds);
}