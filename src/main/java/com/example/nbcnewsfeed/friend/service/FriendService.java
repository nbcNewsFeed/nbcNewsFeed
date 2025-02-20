package com.example.nbcnewsfeed.friend.service;
import com.example.nbcnewsfeed.friend.dto.*;
import com.example.nbcnewsfeed.friend.entity.FriendRequest;
import com.example.nbcnewsfeed.friend.entity.FriendStatus;
import com.example.nbcnewsfeed.friend.entity.Friendship;
import com.example.nbcnewsfeed.friend.repository.FriendRequestRepository;
import com.example.nbcnewsfeed.friend.repository.FriendshipRepository;
import com.example.nbcnewsfeed.user.entity.User;
import com.example.nbcnewsfeed.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendService {
    private final UserService userService;
    private final FriendRequestRepository friendRequestRepository;
    private final FriendshipRepository friendshipRepository;

    @Transactional
    public void sendFriendRequest(Long loginId, Long receiverId) {

        // 받는 사람과 보내는 사람이 실제로 존재하는지 확인
        User senderUser = userService.findUserById(loginId);
        User receiverUser = userService.findUserById(receiverId);

        if(friendshipRepository.existsFriendshipByUser1IdAndUser2Id(loginId, receiverId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 친구 상태입니다.");
        }

        if(friendRequestRepository.existsFriendRequestBySenderIdAndReceiverIdAndFriendStatus(loginId, receiverId, FriendStatus.WAITING)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 친구 요청을 보냈습니다. (내가 보냈던 거 까먹고 다시 보냈을때)");
        }

        if(friendRequestRepository.existsFriendRequestBySenderIdAndReceiverIdAndFriendStatus(receiverId, loginId, FriendStatus.WAITING)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "친구 요청 목록을 확인해주세요. (해당 사람에게 요청이 들어온 줄 모르고 요청을 보냈을때) ");
        }

        FriendRequest friendRequest = new FriendRequest(senderUser, receiverUser);

        friendRequestRepository.save(friendRequest);
    }

    @Transactional
    public void cancelFriendRequest(Long loginId, Long cancelId) {

        userService.findUserById(cancelId);

        FriendRequest friendRequest = friendRequestRepository.findFriendRequestBySenderIdAndReceiverIdAndFriendStatus(loginId, cancelId, FriendStatus.WAITING);

        if(friendRequest==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 id에게 친구 요청 기록이 없습니다.");
        }

        log.info("*********** 친구 요청 목록 : "+ friendRequest.getId());
        friendRequestRepository.delete(friendRequest);

    }

    @Transactional
    public String updateFriendRequest(Long loginId, Long requesterId, UpdateFriendRequestDto requestDto) {

        User senderUser = userService.findUserById(requesterId); // 요청보낸 사람의 Id
        User receiverUser = userService.findUserById(loginId); // 요청받은 사람의 Id (현재 로그인된 사용자)

        if(friendshipRepository.existsFriendshipByUser1IdAndUser2Id(requesterId, loginId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 친구 상태입니다.");
        }

        if(!friendRequestRepository.existsFriendRequestBySenderIdAndReceiverIdAndFriendStatus(requesterId, loginId, FriendStatus.WAITING)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 사용자에게 친구 요청을 받지 않았습니다.");
        }

        // 친구 요청 대기 테이블에서 두 사용자의 요청 현황 뽑아오기
        FriendRequest findFriendRequest = friendRequestRepository.findFriendRequestBySenderIdAndReceiverIdAndFriendStatus(requesterId, loginId, FriendStatus.WAITING);

        if(requestDto.getIsAcceptOrReject()){
            findFriendRequest.updateFriendStatus(FriendStatus.ACCEPTED); //뽑아온 현황에서 친구 상태를 Accepted 로 변경

            Friendship friendship1 = new Friendship(senderUser, receiverUser);
            Friendship friendship2 = new Friendship(receiverUser, senderUser);

            friendshipRepository.save(friendship1);
            friendshipRepository.save(friendship2);

            return "친구 수락 성공!";

        } else {
            findFriendRequest.updateFriendStatus(FriendStatus.REJECTED);
            return "친구 거절 성공";
        }

    }

    public List<User> getFriendshipList(Long userId) {

        friendshipRepository.enableSoftDeleteFilterV2();

        List<Long> friendIdList = friendshipRepository.findFriendIdByUser1Id(userId); // 단순 아이디 나열

        return userService.findUsersByIds(friendIdList);

    }

    @Transactional
    public void deleteFriend(Long loginId, Long deleteId) {

        userService.findUserById(deleteId); //삭제하고싶은 id가 실제로 존재하는지 확인

        User user1 = userService.findUserById(loginId); // 현재 로그인된 사용자
        User user2 = userService.findUserById(deleteId); //삭제하고싶은 사용자

        Friendship friendship1 = friendshipRepository.findFriendshipsByUser1AndUser2(user1, user2);
        Friendship friendship2 = friendshipRepository.findFriendshipsByUser1AndUser2(user2, user1);

        if(friendship1 ==null || friendship2 == null) {
            throw new IllegalStateException("친구관계가 아닙니다.");
        }

        friendship1.softDelete();
        friendship2.softDelete();


    }

    @Transactional
    public void deleteAllFriendByUserId(Long userId){
        User user = userService.findUserById(userId);
        List<Friendship> friendshipsByUser = friendshipRepository.findFriendshipsByUser(user);

        if(friendshipsByUser != null){
            friendshipsByUser.forEach(Friendship::softDelete);
        } else throw new IllegalStateException("해당 아이디는 삭제할 친구가 없습니다.");
    }

    public List<FriendRequestListDto> getFriendRequests(Long loginId) {

        friendshipRepository.enableSoftDeleteFilter();

        List<FriendRequest> friendRequestsList = friendRequestRepository.findFriendRequestBySenderIdOrReceiverIdAndFriendStatus(loginId, loginId, FriendStatus.WAITING);

        return friendRequestsList.stream()
                .map(request -> new FriendRequestListDto(
                        request.getSender().getId(),
                        request.getReceiver().getId(),
                        request.getFriendStatus()
                ))
                .collect(Collectors.toList());
    }

    public List<Long> findFriendIds(Long userId) {
        return friendshipRepository.findFriendIdByUser1Id(userId);
    }
}