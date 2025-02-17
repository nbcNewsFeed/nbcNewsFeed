package com.example.nbcnewsfeed.friend.repository;

import com.example.nbcnewsfeed.friend.entity.Friendship;
import com.example.nbcnewsfeed.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {


    boolean existsFriendshipByUser1IdAndUser2Id(Long senderId, Long receiverId);

    List<Friendship> findFriendshipsByUser1(User user1);

    Friendship findFriendshipsByUser1AndUser2(User user1, User user2);
}
