package com.example.nbcnewsfeed.friend.repository;

import com.example.nbcnewsfeed.friend.entity.Friendship;
import com.example.nbcnewsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long>, FriendshipRepositoryCustom {

    boolean existsFriendshipByUser1IdAndUser2Id(Long senderId, Long receiverId);

    @Query("SELECT f FROM Friendship f WHERE f.user1=:user OR f.user2=:user")
    List<Friendship> findFriendshipsByUser(@Param("user") User user);

    Friendship findFriendshipsByUser1AndUser2(User user1, User user2);

    //친구 리스트 반환
    @Query("SELECT f.user2.id FROM Friendship f WHERE f.user1.id = :userId")
    List<Long> findFriendIdByUser1Id(Long userId);

}
