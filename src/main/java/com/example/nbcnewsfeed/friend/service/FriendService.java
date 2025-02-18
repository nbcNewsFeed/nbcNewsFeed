package com.example.nbcnewsfeed.friend.service;

import com.example.nbcnewsfeed.friend.dto.CreateFriendRequestDto;
import com.example.nbcnewsfeed.friend.dto.DeleteFriendshipDto;
import com.example.nbcnewsfeed.friend.dto.FriendshipListDto;
import com.example.nbcnewsfeed.friend.dto.UpdateFriendRequestDto;
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
    public void sendFriendRequest(CreateFriendRequestDto requestDto) {

        // 받는 사람과 보내는 사람이 실제로 존재하는지 확인
        User senderUser = userService.findUserById(requestDto.getSenderId());
        User receiverUser = userService.findUserById(requestDto.getReceiverId());

        Long senderId = senderUser.getId();
        Long receiverId = receiverUser.getId();


        if(friendshipRepository.existsFriendshipByUser1IdAndUser2Id(senderId, receiverId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 친구 상태입니다.");
        }

        if(friendRequestRepository.existsFriendRequestBySenderIdAndReceiverIdAndFriendStatus(senderId, receiverId, FriendStatus.WAITING)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 친구 요청을 보냈습니다. (내가 보냈던 거 까먹고 다시 보냈을때)");
        }

        if(friendRequestRepository.existsFriendRequestBySenderIdAndReceiverIdAndFriendStatus(receiverId, senderId, FriendStatus.WAITING)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "친구 요청 목록을 확인해주세요. (해당 사람에게 요청이 들어온 줄 모르고 요청을 보냈을때) ");
        }

        FriendRequest friendRequest = new FriendRequest(senderUser, receiverUser);

        friendRequestRepository.save(friendRequest);
    }

    @Transactional
    public String updateFriendRequest(UpdateFriendRequestDto requestDto) {

        User senderUser = userService.findUserById(requestDto.getSenderId());
        User receiverUser = userService.findUserById(requestDto.getReceiverId());

        Long senderId = senderUser.getId();
        Long receiverId = receiverUser.getId();

        if(friendshipRepository.existsFriendshipByUser1IdAndUser2Id(senderId, receiverId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 친구 상태입니다.");
        }

        if(!friendRequestRepository.existsFriendRequestBySenderIdAndReceiverIdAndFriendStatus(senderId, receiverId, FriendStatus.WAITING)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 사용자에게 친구 요청을 받지 않았습니다.");
        }

        FriendRequest findFriendRequest = friendRequestRepository.findFriendRequestBySenderIdAndReceiverIdAndFriendStatus(senderId, receiverId, FriendStatus.WAITING);

        if(requestDto.getIsAcceptOrReject()){
            findFriendRequest.updateFriendStatus(FriendStatus.ACCEPTED);

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

    public List<FriendshipListDto> getFriendshipList(Long userId) {

        User user = userService.findUserById(userId);

        List<Friendship> friendshipList = friendshipRepository.findFriendshipsByUser1(user);

        return friendshipList.stream().map(FriendshipListDto::toDto).collect(Collectors.toList());
    }

    @Transactional
    public void deleteFriend(DeleteFriendshipDto requestDto) {
        User user1 = userService.findUserById(requestDto.getUser1Id());
        User user2 = userService.findUserById(requestDto.getUser2Id());

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
}