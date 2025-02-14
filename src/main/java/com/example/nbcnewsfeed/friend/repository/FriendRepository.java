package com.example.nbcnewsfeed.friend.repository;

import com.example.nbcnewsfeed.friend.entity.Friend;
import com.example.nbcnewsfeed.friend.entity.FriendStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

//    boolean existsBySenderIdAndReceiverIdAndStatus(Long senderId, Long receiverId, FriendStatus friendStatus);

    @Query("SELECT COUNT(f) > 0 FROM Friend f WHERE (f.sender.id = :senderId AND f.receiver.id = :receiverId OR f.sender.id = :receiverId AND f.receiver.id = :senderId) AND f.status = :status")
    boolean existsBySenderIdAndReceiverIdAndStatus(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId, @Param("status") FriendStatus status);

}
