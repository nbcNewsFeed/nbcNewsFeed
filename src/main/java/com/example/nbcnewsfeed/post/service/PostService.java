//package com.example.nbcnewsfeed.post.service;
//
//import com.example.nbcnewsfeed.post.dto.request.PostSaveRequestDto;
//import com.example.nbcnewsfeed.post.dto.request.PostUpdateRequestDto;
//import com.example.nbcnewsfeed.post.dto.response.PostPageResponseDto;
//import com.example.nbcnewsfeed.post.dto.response.PostResponseDto;
//import com.example.nbcnewsfeed.post.dto.response.PostSaveResponseDto;
//import com.example.nbcnewsfeed.post.dto.response.PostUpdateResponseDto;
//import com.example.nbcnewsfeed.post.entity.Post;
//import com.example.nbcnewsfeed.post.repository.PostRepository;
//import jakarta.validation.constraints.Null;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.http.HttpStatus;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class PostService {
//
//    private final PostRepository postRepository;
////    private final UserRepository userRepository;
//
//    @Transactional
//    public PostSaveResponseDto save(
////            Long userId,
//            PostSaveRequestDto requestDto) {
////        User user = userRepository.findById(userId);
//        Post post = new Post(
////                user,
//                requestDto.getImageUrl(),
//                requestDto.getContents());
//        postRepository.save(post);
//        return new PostSaveResponseDto(
//                post.getId(),
////                user.getUserId(),
//                post.getImageUrl(),
//                post.getContents(),
//                post.getCreatedAt(),
//                post.getModifiedAt()
//        );
//    }
//
//    // 한 페이지 당 10 개의 post 조회
//    @Transactional(readOnly = true)
//    public Page<PostPageResponseDto> findAllPage(int page, int size) {
//        // 클라이언트에서 1부터 전달된 페이지 번호를 0 기반으로 조정
//        int adjustedPage = (page > 0) ? page - 1 : 0;
//        // pageable 객체 생성, 수정일 기준 내림차순 정렬
//        PageRequest pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
//        // Post Page 조회
//        Page<Post> postPage = postRepository.findAll(pageable);
//        // Post ID 리스트 추출
//        List<Long> postIds = postPage.stream()
//                .map(Post::getId)
//                .collect(Collectors.toList());
//        // CommentCountDto 클래스, countByPostIds 메서드 필요
//        // 별도 쿼리로 댓글 수 조회
//        //todo domain 별 repository 분리 -> commentservice 에서 commentrepository 로 접근
//        List<CommentCountDto> countResults = commentRepository.countByPostIds(postIds);
//        Map<Long, Long> commentCountMap = countResults.stream()
//                .collect(Collectors.toMap(CommentCountDto::getPostId, CommentCountDto::getCount));
//        // 각 Post를 PostPageResponseDto로 변환 (댓글 수는 Long을 int로 변환)
//        return postPage.map(post -> new PostPageResponseDto(
//                post.getId(),
////                post.getUserId(),
//                post.getImageUrl(),
//                post.getContents(),
//                commentCountMap.getOrDefault(post.getId(), 0L).intValue(),
//                post.getCreatedAt(),
//                post.getModifiedAt()
//        ));
//    }
//
//    @Transactional(readOnly = true)
//    public PostResponseDto findById(Long id) {
//        //post null 검증
//        Post post = postRepository.findById(id).orElseThrow(
//                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
//        );
//        return new PostResponseDto(
//                post.getId(),
////                        post.getUser().getId(),
//                post.getImageUrl(),
//                post.getContents(),
//                post.getCreatedAt(),
//                post.getModifiedAt()
//        );
//    }
//
//    @Transactional
//    public PostUpdateResponseDto update(
//            Long postId,
////            Long userId,
//            PostUpdateRequestDto requestDto) {
//        //post null 검증
//        Post post = postRepository.findById(postId).orElseThrow(
//                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
//        );
//        //post 작성자가 맞는지 검증
////        if(!userId.equals(post.getUser().getId())) {
////            throw new IllegalArgumentException("작성자 본인만 수정할 수 있습니다.");
////        }
//        post.update(requestDto.getImageUrl(), requestDto.getContents());
//        return new PostUpdateResponseDto(
//                post.getId(),
////                        post.getUser().getId(),
//                post.getImageUrl(),
//                post.getContents(),
//                post.getCreatedAt(),
//                post.getModifiedAt()
//        );
//    }
//
//    @Transactional
//    public void deleteById(
//            Long postId
////            , Long userId
//    ) {
//        //post null 검증
//        Post post = postRepository.findById(postId).orElseThrow(
//                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
//        );
//        //post 작성자가 맞는지 검증
////        if(!userId.equals(post.getUser().getId())) {
////            throw new IllegalArgumentException("작성자 본인만 삭제할 수 있습니다.");
////        }
//        post.createDeletedAt(LocalDateTime.now());
//    }
//
//    // 2주가 지난 사용자 물리 삭제
//    @Transactional
//    @Scheduled(cron = "0 0 3 * * ?")
//    public void deletePosts() {
//        LocalDateTime twoWeeksAgo = LocalDateTime.now().minusWeeks(2);
//        List<Post> postToDelete = postRepository.findAllByDeletedAtBefore(twoWeeksAgo);
//        if (!postToDelete.isEmpty()) {
//            postRepository.deleteAll(postToDelete);
//        }
//    }
//
//    @Transactional
//    public PostResponseDto restore(Long postId) {
//        //post null 검증
//        Post post = postRepository.findById(postId).orElseThrow(
//                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
//        );
//        //post 작성자가 맞는지 검증
////        if(!userId.equals(post.getUser().getId())) {
////            throw new IllegalArgumentException("작성자 본인만 게시글을 복구할 수 있습니다.");
////        }
//        post.createDeletedAt(null);
//        return new PostResponseDto(
//                post.getId(),
////                        post.getUser().getId(),
//                post.getImageUrl(),
//                post.getContents(),
//                post.getCreatedAt(),
//                post.getModifiedAt()
//        );
//    }
//}
