package com.example.nbcnewsfeed.comment.service;

import com.example.nbcnewsfeed.comment.dto.request.CommentSaveRequestDto;
import com.example.nbcnewsfeed.comment.dto.request.CommentUpdateRequestDto;
import com.example.nbcnewsfeed.comment.dto.response.CommentResponseDto;
import com.example.nbcnewsfeed.comment.entity.Comment;
import com.example.nbcnewsfeed.comment.repository.CommentRepository;
import com.example.nbcnewsfeed.post.entity.Post;
import com.example.nbcnewsfeed.post.repository.PostRepository;
import com.example.nbcnewsfeed.user.entity.User;
import com.example.nbcnewsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // Create
    @Transactional
    public CommentResponseDto save(
            Long userId,
            Long postId,
            CommentSaveRequestDto dto
    ) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        Comment comment = new Comment(post, user, dto.getContent());

        commentRepository.save(comment);

        return new CommentResponseDto(
                comment.getId(),
                user.getId(),
                post.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

    // Read
    @Transactional(readOnly = true)
    public List<CommentResponseDto> findByPost(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream()
                .map(comment -> new CommentResponseDto(
                        comment.getId(),
                        comment.getUser().getId(),
                        comment.getPost().getId(),
                        comment.getContent(),
                        comment.getCreatedAt(),
                        comment.getModifiedAt()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CommentResponseDto findOne(Long id) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        return new CommentResponseDto(
                comment.getId(),
                comment.getUser().getId(),
                comment.getPost().getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getModifiedAt());
    }

    // Update
    @Transactional
    public CommentResponseDto update(
            Long commentId,
            Long userId,
            CommentUpdateRequestDto dto
    ) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        if (!comment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("본인이 작성한 댓글만 수정할 수 있습니다.");
        }

        comment.update(dto.getContent());

        return new CommentResponseDto(
                comment.getId(),
                comment.getUser().getId(),
                comment.getPost().getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getModifiedAt());
    }

    @Transactional
    public void delete(Long commentId, Long userId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        if (!comment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("본인이 작성한 댓글만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);
    }
}