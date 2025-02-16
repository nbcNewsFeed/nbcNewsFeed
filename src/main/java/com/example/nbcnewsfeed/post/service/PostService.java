package com.example.nbcnewsfeed.post.service;

import com.example.nbcnewsfeed.post.dto.request.PostSaveRequestDto;
import com.example.nbcnewsfeed.post.dto.request.PostUpdateRequestDto;
import com.example.nbcnewsfeed.post.dto.response.PostResponseDto;
import com.example.nbcnewsfeed.post.dto.response.PostSaveResponseDto;
import com.example.nbcnewsfeed.post.dto.response.PostUpdateResponseDto;
import com.example.nbcnewsfeed.post.entity.Post;
import com.example.nbcnewsfeed.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public List<PostResponseDto> findAll() {
        return postRepository.findAll().stream()
                .map(post -> new PostResponseDto(
                        post.getId(),
//                        post.getUser().getId(),
                        post.getImageUrl(),
                        post.getContents(),
                        post.getCreatedAt(),
                        post.getModifiedAt()
                )
        ).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostResponseDto findById(Long id) {
        //post null 검증
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );
        return new PostResponseDto(
                post.getId(),
//                        post.getUser().getId(),
                post.getImageUrl(),
                post.getContents(),
                post.getCreatedAt(),
                post.getModifiedAt()
        );
    }

    @Transactional
    public PostUpdateResponseDto update(
            Long postId,
//            Long userId,
            PostUpdateRequestDto requestDto) {
        //post null 검증
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );
        //post 작성자가 맞는지 검증
//        if(!userId.equals(post.getUser().getId())) {
//            throw new IllegalArgumentException("작성자 본인만 수정할 수 있습니다.");
//        }
        post.update(requestDto.getImageUrl(), requestDto.getContents());
        return new PostUpdateResponseDto(
                post.getId(),
//                        post.getUser().getId(),
                post.getImageUrl(),
                post.getContents(),
                post.getCreatedAt(),
                post.getModifiedAt()
        );
    }
}
