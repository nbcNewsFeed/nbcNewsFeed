package com.example.nbcnewsfeed.friend.service;

import com.example.nbcnewsfeed.friend.entity.Friend;
import com.example.nbcnewsfeed.friend.entity.FriendStatus;
import com.example.nbcnewsfeed.friend.repository.FriendRepository;
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
    private final FriendRepository friendRepository;
    private final UserClient userClient;

    @Transactional
    public void sendFriendRequest(Long senderId, Long receiverId) {

        // 받는 사람과 보내는 사람이 실제로 존재하는지 확인
        User senderUser = userClient.findUserById(senderId);
        User receiverUser = userClient.findUserById(receiverId);

        // 이미 친구 상태인지 확인하는 로직
        if (friendRepository.existsBySenderIdAndReceiverIdAndFriendStatus(senderId, receiverId, FriendStatus.ACCEPTED)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 친구 상태입니다.");
        }

        // 요청 대기 상태인지 확인하는 로직
        if (friendRepository.existsBySenderIdAndReceiverIdAndFriendStatus(senderId, receiverId, FriendStatus.WAITING)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "친구 요청 대기 상태입니다.");
        }

        Friend friend = new Friend(senderUser, receiverUser, FriendStatus.WAITING);
        friendRepository.save(friend);

    }


}
