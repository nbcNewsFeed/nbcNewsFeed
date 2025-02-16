package com.example.nbcnewsfeed.post.repository;

import com.example.nbcnewsfeed.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
