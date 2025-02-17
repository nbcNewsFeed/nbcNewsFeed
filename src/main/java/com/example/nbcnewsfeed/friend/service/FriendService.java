package com.example.nbcnewsfeed.friend.service;

import com.example.nbcnewsfeed.friend.dto.CreateFriendRequestDto;
import com.example.nbcnewsfeed.friend.entity.FriendRequest;
import com.example.nbcnewsfeed.friend.entity.FriendStatus;
import com.example.nbcnewsfeed.friend.repository.FriendRequestRepository;
import com.example.nbcnewsfeed.friend.repository.FriendshipRepository;
import com.example.nbcnewsfeed.user.User;
import com.example.nbcnewsfeed.user.UserClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRequestRepository friendRepository;
    private final UserClient userClient;
    private final FriendRequestRepository friendRequestRepository;
    private final FriendshipRepository friendshipRepository;

    @Transactional
    public void sendFriendRequest(CreateFriendRequestDto requestDto) {

        // 받는 사람과 보내는 사람이 실제로 존재하는지 확인
        User senderUser = userClient.findUserById(requestDto.getSenderId());
        User receiverUser = userClient.findUserById(requestDto.getReceiverId());

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

        FriendRequest friendRequest = new FriendRequest(senderUser, receiverUser, FriendStatus.WAITING);

        friendRequestRepository.save(friendRequest);
    }


}
