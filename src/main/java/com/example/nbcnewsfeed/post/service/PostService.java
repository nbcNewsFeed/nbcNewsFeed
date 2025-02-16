package com.example.nbcnewsfeed.post.service;

import com.example.nbcnewsfeed.post.dto.request.PostSaveRequestDto;
import com.example.nbcnewsfeed.post.dto.response.PostSaveResponseDto;
import com.example.nbcnewsfeed.post.entity.Post;
import com.example.nbcnewsfeed.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
//    private final UserRepository userRepository;

    @Transactional
    public PostSaveResponseDto save(
            //userID 받아야 함
//            @SessionAttribute(name = Const.LOGIN_USER) Long userId;
            //imageUrl, contents 입력받음
            PostSaveRequestDto requestDto) {
//        User user = userRepository.findById(userId);
        Post post = new Post(
//                user,
                requestDto.getImageUrl(),
                requestDto.getContents());
        postRepository.save(post);
        return new PostSaveResponseDto(
                post.getId(),
//                user.getUserId(),
                post.getImageUrl(),
                post.getContents(),
                post.getCreatedAt(),
                post.getModifiedAt()
        );
    }
}
