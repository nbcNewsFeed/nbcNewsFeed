package com.example.nbcnewsfeed.friend.repository;

import com.example.nbcnewsfeed.friend.entity.FriendRequest;
import com.example.nbcnewsfeed.friend.entity.FriendStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    boolean existsFriendRequestBySenderIdAndReceiverIdAndFriendStatus(Long senderId, Long receiverId, FriendStatus friendStatus);

    FriendRequest findFriendRequestBySenderIdAndReceiverIdAndFriendStatus(Long senderId, Long receiverId, FriendStatus friendStatus);
}